package com.siddworks.productplanner.utils

import android.content.Context
import com.siddworks.productplanner.extensions.getSharedPrefs

class Config(context: Context) {
    private val prefs = context.getSharedPrefs()

    companion object {
        fun newInstance(context: Context) = Config(context)
    }

    public var firmId: String?
        get() = prefs.getString("firm_id", null)
        set(firmIdString) = prefs.edit().putString("firm_id", firmIdString).apply()
}