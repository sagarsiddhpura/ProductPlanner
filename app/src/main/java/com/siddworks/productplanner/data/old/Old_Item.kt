package com.siddworks.productplanner.data.old

data class Old_Item (
    var id: Long = 0,
    var itemCode: String = "",
    var itemName: String = "",
    var photoUrl: String = "",
    var catagory: String = "",
    var labour: String = "",
    var profit: String = "",
    var dealerProfit: String = "",
    var Materials: ArrayList<Old_Material> = arrayListOf()
)