package com.siddworks.productplanner.extensions

import android.app.Activity
import android.app.ProgressDialog.show
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.siddworks.productplanner.R
import com.siddworks.productplanner.utils.isJellyBean1Plus
import com.siddworks.productplanner.utils.isOnMainThread

fun Activity.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    if (isOnMainThread()) {
        showToast(this, msg, length)
    } else {
        runOnUiThread {
            showToast(this, msg, length)
        }
    }
}

private fun showToast(activity: Activity, message: String, length: Int) {
    if (!activity.isActivityDestroyed()) {
        activity.applicationContext.toast(message, length)
    }
}

fun Activity.isActivityDestroyed() = isJellyBean1Plus() && isDestroyed

fun Activity.setupDialogStuff(view: View, dialog: AlertDialog, titleId: Int = 0, titleText: String = "", callback: (() -> Unit)? = null) {
    if (isActivityDestroyed()) {
        return
    }

    dialog.apply {
        setView(view)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(true)
        show()
    }
    callback?.invoke()
}
