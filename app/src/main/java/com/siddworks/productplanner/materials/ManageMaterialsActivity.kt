package com.siddworks.productplanner.materials

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siddworks.productplanner.BaseActivity
import com.siddworks.productplanner.R
import com.siddworks.productplanner.data.Material
import com.siddworks.productplanner.extensions.dataSource
import com.siddworks.productplanner.extensions.toast
import com.siddworks.productplanner.utils.addVerticalDividers
import com.siddworks.productplanner.utils.setupSystemUI
import kotlinx.android.synthetic.main.activity_manage_materials.*


class ManageMaterialsActivity : BaseActivity() {

    private lateinit var mats: ArrayList<Material>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_materials)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Manage Materials"
        setupSystemUI(this)

        dataSource.getMaterials(this) {
            if(it != null) {
                it.sortByDescending { it.order }
                mats = it
                // Creates a vertical Layout Manager
                val layoutManager = materials_rv.layoutManager as GridLayoutManager
                layoutManager.orientation = RecyclerView.VERTICAL
                layoutManager.spanCount = 1

                // Access the RecyclerView Adapter and load the data into it
                ManageMaterialsAdapter(it, this).apply {
                    materials_rv.adapter = this
                    addVerticalDividers(this@ManageMaterialsActivity, materials_rv, true)
                }
            } else {
                toast("Error opening Course")
                finish()
                return@getMaterials
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_manage, menu)
        menu.apply {
            findItem(R.id.manage_add).isVisible = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.manage_add -> addEntity()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun addEntity() {
        val highestId = mats.maxBy { it.order }
        var nextId = 1L
        if(highestId != null) {
            nextId = highestId.order.plus(1)
        }
        val material = Material("mat_" + nextId, "", "", 0.0, nextId)
        EditMaterialDialog(this, material) {
            mats.add(it)
            refreshMaterials(mats)
            dataSource.addMaterial(this, material)
        }
    }

    private fun refreshMaterials(materials: ArrayList<Material>) {
        materials.sortByDescending { it.order }
        getRecyclerAdapter()?.updateEntities(materials)
    }

    private fun getRecyclerAdapter() = materials_rv.adapter as? ManageMaterialsAdapter

    fun delete(material: Material) {
        val builder = AlertDialog.Builder(this@ManageMaterialsActivity)
        builder.setMessage("Are you sure you want to delete this Material?")
            .setTitle("Delete Material")
        builder.setPositiveButton("YES") { dialog, itemId ->
            val iterator = mats.iterator()
            while (iterator.hasNext()) {
                val currentItem = iterator.next()
                if (currentItem.id == material.id) {
                    iterator.remove()
                    break
                }
            }
            refreshMaterials(mats)
            dataSource.removeMaterial(this, material)
            Toast.makeText(applicationContext, "Material deleted...", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("CANCEL", null)
        val dialog = builder.create()
        dialog.show()
    }

    fun edit(material: Material) {
        EditMaterialDialog(this, material) {
            val iterator = mats.iterator()
            while (iterator.hasNext()) {
                val currentItem = iterator.next()
                if (currentItem.id == material.id) {
                    iterator.remove()
                    break
                }
            }

            mats.add(material)
            refreshMaterials(mats)
            dataSource.updateMaterial(this, material)
        }
    }
}
