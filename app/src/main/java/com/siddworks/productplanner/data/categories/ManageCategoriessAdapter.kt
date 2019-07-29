package com.siddworks.productplanner.data.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siddworks.productplanner.R
import com.siddworks.productplanner.data.Category
import com.siddworks.productplanner.extensions.beGone
import kotlinx.android.synthetic.main.item_material.view.*

class ManageCategoriessAdapter(private var items : ArrayList<Category>, private val context: ManageCategoryActivity) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_material, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val material = items[position]
        holder.name.text = material.name
        holder.desc.beGone()
        holder.deleteIcon.setOnClickListener {
            context.delete(material)
        }
        holder.root.setOnClickListener {
            context.edit(material)
        }
    }

    fun updateEntities(materials: ArrayList<Category>) {
        items = materials.clone() as java.util.ArrayList<Category>
        notifyDataSetChanged()
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val name = view.entity_name!!
    val desc = view.entity_desc!!
    val deleteIcon = view.delete_entity_image!!
    val root = view.entity_holder!!
}