package com.siddworks.productplanner.data

data class Material (
    var id: String = "",
    var name: String = "",
    var unit: String = "",
    var unitPrice: Double = 0.0,
    var order: Long = 0
) {
    override fun toString(): String {
        return "$name:::$unit"
    }
}
