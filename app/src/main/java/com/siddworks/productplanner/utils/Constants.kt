package com.siddworks.productplanner.utils

import android.app.Activity
import android.os.Build
import android.os.Looper
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.siddworks.productplanner.R

const val PREFS_KEY = "Prefs"

fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()
fun isJellyBean1Plus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
fun addVerticalDividers(activity: Activity, recyclerView:RecyclerView, add: Boolean) {
    if (recyclerView.itemDecorationCount > 0) {
        recyclerView.removeItemDecorationAt(0)
    }

    if (add) {
        DividerItemDecoration(activity, DividerItemDecoration.VERTICAL).apply {
            setDrawable(activity.resources.getDrawable(R.drawable.divider))
            recyclerView.addItemDecoration(this)
        }
    }
}