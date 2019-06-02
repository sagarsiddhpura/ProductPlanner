package com.siddworks.productplanner.data

import android.app.Activity
import com.siddworks.productplanner.materials.Material


class DebugDataSource {
    fun initMockDataRealtimeDatabase(activity: Activity,  dataSource: DataSource) {
        val dbRef = dataSource.getMatsDatabase(activity)

        val m1 = Material(1, "80MM NB PIPE", "No's", 142)
        val m2 = Material(2, "BEARING SHAFT", "No's", 2000)
        dbRef.setValue(linkedMapOf(m1.id.toString() to m1, m2.id.toString() to m2))
        dbRef.push()
    }
}
