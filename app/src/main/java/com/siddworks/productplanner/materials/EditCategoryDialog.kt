package com.siddworks.productplanner.materials

import android.view.View
import androidx.appcompat.app.AlertDialog
import com.siddworks.productplanner.BaseActivity
import com.siddworks.productplanner.R
import com.siddworks.productplanner.data.Category
import com.siddworks.productplanner.extensions.beGone
import com.siddworks.productplanner.extensions.setupDialogStuff
import com.siddworks.productplanner.extensions.toast
import kotlinx.android.synthetic.main.dialog_edit_material.view.*

class EditCategoryDialog(private val activity: BaseActivity, val item: Category, val callback: (item: Category) -> Unit) {
    init {
        val view = activity.layoutInflater.inflate(R.layout.dialog_edit_material, null)
        view.edit_material_name.setText(item.name)
        view.edit_material_unit.beGone()
        view.edit_material_unit_price.beGone()

        AlertDialog.Builder(activity)
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create().apply {
                activity.setupDialogStuff(view, this, 0,"Edit Category") {
                    getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        when {
                            view.edit_material_name.text.isEmpty() -> activity.toast("Please enter Name")
                            else -> sendSuccess(this, view, item)
                        }
                    }
                }
            }
    }

    private fun sendSuccess(alertDialog: AlertDialog, view : View, item: Category) {
        item.name = view.edit_material_name.text.toString()
        callback(item)
        alertDialog.dismiss()
    }
}
