package com.siddworks.productplanner.materials

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siddworks.productplanner.BaseActivity
import com.siddworks.productplanner.R
import com.siddworks.productplanner.extensions.dataSource
import com.siddworks.productplanner.extensions.toast
import com.siddworks.productplanner.utils.addVerticalDividers
import kotlinx.android.synthetic.main.activity_manage_materials.*


class ManageMaterialsActivity : BaseActivity() {

    private lateinit var mats: ArrayList<Material>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.siddworks.productplanner.R.layout.activity_manage_materials)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Manage Materials"

        dataSource.getMaterials(this) {
            if(it != null) {
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
        val highestId = mats.maxBy { it.id }
        var nextId = 1L
        if(highestId != null) {
            nextId = highestId.id.plus(1)
        }
        val material = Material(nextId, "", "", 0)
        EditMaterialDialog(this, material) {
            mats.add(it)
            refreshMaterials(mats)
            dataSource.addMaterial(this, material)
        }
    }

    private fun refreshMaterials(materials: ArrayList<Material>) {
        getRecyclerAdapter()?.updateEntities(materials)
    }

    private fun getRecyclerAdapter() = materials_rv.adapter as? ManageMaterialsAdapter
}
