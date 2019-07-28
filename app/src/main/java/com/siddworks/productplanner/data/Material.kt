package com.siddworks.productplanner.data

data class Material (
    var id: Long = 0,
    var name: String = "",
    var unit: String = "",
    var unitPrice: Double = 0.0
) {
    override fun toString(): String {
        return "$name:::$unit"
    }
}
