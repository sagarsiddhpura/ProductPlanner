package com.siddworks.productplanner.materials

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siddworks.productplanner.R
import kotlinx.android.synthetic.main.item_material.view.*

class ManageMaterialsAdapter(private var items : ArrayList<Material>, private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

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
        var desc = "Price:" + material.unitPrice.toString()
        if(material.unit != null && material.unit != "") {
            desc += ", Unit:" + material.unit
        }
        holder.desc.text = desc
    }

    fun updateEntities(materials: ArrayList<Material>) {
        items = materials.clone() as java.util.ArrayList<Material>
        notifyDataSetChanged()
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val name = view.entity_name!!
    val desc = view.entity_desc!!
}