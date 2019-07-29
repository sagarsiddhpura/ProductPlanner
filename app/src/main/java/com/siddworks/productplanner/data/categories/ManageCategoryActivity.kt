package com.siddworks.productplanner.data.categories

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siddworks.productplanner.BaseActivity
import com.siddworks.productplanner.R
import com.siddworks.productplanner.data.Category
import com.siddworks.productplanner.extensions.dataSource
import com.siddworks.productplanner.extensions.toast
import com.siddworks.productplanner.materials.EditCategoryDialog
import com.siddworks.productplanner.utils.addVerticalDividers
import com.siddworks.productplanner.utils.setupSystemUI
import kotlinx.android.synthetic.main.activity_manage_materials.*

class ManageCategoryActivity : BaseActivity() {

    private lateinit var items: ArrayList<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_materials)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Manage Categories"
        setupSystemUI(this)

        dataSource.getCategories(this) {
            if(it != null) {
                it.sortByDescending { it.order }
                items = it
                // Creates a vertical Layout Manager
                val layoutManager = materials_rv.layoutManager as GridLayoutManager
                layoutManager.orientation = RecyclerView.VERTICAL
                layoutManager.spanCount = 1

                // Access the RecyclerView Adapter and load the data into it
                ManageCategoriessAdapter(it, this).apply {
                    materials_rv.adapter = this
                    addVerticalDividers(this@ManageCategoryActivity, materials_rv, true)
                }
            } else {
                toast("Error opening Course")
                finish()
                return@getCategories
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
        val highestId = items.maxBy { it.order }
        var nextId = 1L
        if(highestId != null) {
            nextId = highestId.order.plus(1)
        }
        val item = Category("cat_" + nextId, "", nextId)
        EditCategoryDialog(this, item) {
            items.add(it)
            refreshItems(items)
            dataSource.addCategory(this, item)
        }
    }

    private fun refreshItems(categories: ArrayList<Category>) {
        categories.sortByDescending { it.order }
        getRecyclerAdapter()?.updateEntities(categories)
    }

    private fun getRecyclerAdapter() = materials_rv.adapter as? ManageCategoriessAdapter

    fun delete(item: Category) {
        val builder = AlertDialog.Builder(this@ManageCategoryActivity)
        builder.setMessage("Are you sure you want to delete this Category?")
            .setTitle("Delete Category")
        builder.setPositiveButton("YES") { dialog, itemId ->
            val iterator = items.iterator()
            while (iterator.hasNext()) {
                val currentItem = iterator.next()
                if (currentItem.id == item.id) {
                    iterator.remove()
                    break
                }
            }
            refreshItems(items)
            dataSource.removeCategory(this, item)
            Toast.makeText(applicationContext, "Category deleted...", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("CANCEL", null)
        val dialog = builder.create()
        dialog.show()
    }

    fun edit(item: Category) {
        EditCategoryDialog(this, item) {
            val iterator = items.iterator()
            while (iterator.hasNext()) {
                val currentItem = iterator.next()
                if (currentItem.id == item.id) {
                    iterator.remove()
                    break
                }
            }

            items.add(item)
            refreshItems(items)
            dataSource.updateCategory(this, item)
        }
    }
}
