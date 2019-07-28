package com.siddworks.productplanner.data

data class PriceModifier (
    var id: Long = 0,
    var name: String = "",
    var value: Double = 0.0,
    var order: Int = 0
)
