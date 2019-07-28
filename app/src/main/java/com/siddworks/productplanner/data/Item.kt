package com.siddworks.productplanner.data

import com.siddworks.productplanner.data.ItemMaterial
import com.siddworks.productplanner.data.PriceModifier

data class Item (
    var id: Long = 0,
    var itemCode: String = "",
    var itemName: String = "",
    var image: String = "",
    var categoryId: Long = 0,
    var itemMaterials: ArrayList<ItemMaterial> = arrayListOf(),
    var priceModifiers: ArrayList<PriceModifier> = arrayListOf(),
    var order: Int = 0
)