package com.siddworks.productplanner.materials

data class Material (
    var id: Long = 0,
    var name: String = "",
    var unit: String = "",
    var unitPrice: Long = 0L
) {
    override fun toString(): String {
        return "$name:::$unit"
    }
}
