package com.siddworks.productplanner.materials

import android.view.View
import androidx.appcompat.app.AlertDialog
import com.siddworks.productplanner.BaseActivity
import com.siddworks.productplanner.R
import com.siddworks.productplanner.extensions.setupDialogStuff
import com.siddworks.productplanner.extensions.toast
import kotlinx.android.synthetic.main.dialog_edit_material.view.*

class EditMaterialDialog(private val activity: BaseActivity, val material: Material, val callback: (material: Material) -> Unit) {
    init {
        val view = activity.layoutInflater.inflate(R.layout.dialog_edit_material, null)
        view.edit_material_name.setText(material.name)
        view.edit_material_unit.setText(material.unit)
        view.edit_material_unit_price.setText(material.unitPrice.toString())

        AlertDialog.Builder(activity)
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create().apply {
                activity.setupDialogStuff(view, this, 0,"Edit Material") {
                    getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        when {
                            view.edit_material_name.text.isEmpty() -> activity.toast("Please enter Name")
                            view.edit_material_unit_price.text.isEmpty() -> activity.toast("Please enter Price")

                            else -> sendSuccess(this, view, material)
                        }
                    }
                }
            }
    }

    private fun sendSuccess(alertDialog: AlertDialog, view : View, material: Material) {
        material.name = view.edit_material_name.text.toString()
        material.unit = view.edit_material_unit.text.toString()
        material.unitPrice = view.edit_material_unit_price.text.toString().toLong()

        callback(material)
        alertDialog.dismiss()
    }
}
