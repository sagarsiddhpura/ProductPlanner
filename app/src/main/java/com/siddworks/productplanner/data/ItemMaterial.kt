package com.siddworks.productplanner.data

data class ItemMaterial (
    var id: Long = 0,
    var materialId: String = "",
    var units: Double = 0.0,
    var order: Int = 0
)
