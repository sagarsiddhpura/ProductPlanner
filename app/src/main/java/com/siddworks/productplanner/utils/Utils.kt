package com.siddworks.productplanner.utils

import android.app.Activity
import android.os.Build
import android.view.View
import com.siddworks.productplanner.R

fun setupSystemUI(activity: Activity) {
    val window = activity.window
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        window.statusBarColor = activity.resources.getColor(R.color.teal_200)
    } else {
        window.statusBarColor = activity.resources.getColor(R.color.white, null)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
