package com.siddworks.productplanner.extensions

import android.content.Context
import android.widget.Toast
import com.siddworks.productplanner.data.DataSource
import com.siddworks.productplanner.data.DebugDataSource
import com.siddworks.productplanner.utils.Config
import com.siddworks.productplanner.utils.PREFS_KEY


val Context.dataSource: DataSource get() = DataSource()
val Context.debugDataSource: DebugDataSource get() = DebugDataSource()
val Context.config: Config get() = Config.newInstance(applicationContext)
fun Context.getSharedPrefs() = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    try {
        Toast.makeText(applicationContext, msg, length).show()
    } catch (e: Exception) {
    }
}
