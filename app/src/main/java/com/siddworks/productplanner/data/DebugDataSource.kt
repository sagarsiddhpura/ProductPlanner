package com.siddworks.productplanner.data

import android.app.Activity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.siddworks.productplanner.data.old.Old_Item
import com.siddworks.productplanner.data.old.Old_Material
import com.siddworks.productplanner.extensions.config


class DebugDataSource {
    fun initMockDataRealtimeDatabase(activity: Activity,  dataSource: DataSource) {
//        importMaterials(activity, dataSource)
        importCategories(activity, dataSource)
//        importSwings(activity, dataSource)
//        importSlides(activity, dataSource)
//        importMerryGoRound(activity, dataSource)
//        importItems(activity.config.firmId+"/$matsDbVersion/old_data/multiplay_old/multiplay_old", 8, 24, activity, dataSource)
//        importItems(activity.config.firmId+"/$matsDbVersion/old_data/seesaw_old/seesaw_old", 4, 28, activity, dataSource)
//        importItems(activity.config.firmId+"/$matsDbVersion/old_data/climbers_old/climbers_old", 3, 31, activity, dataSource)
//        importItems(activity.config.firmId+"/$matsDbVersion/old_data/exercise_old/exercise_old", 6, 40, activity, dataSource)
    }

    private fun importItems(location: String, catId: Int, totalItems: Int, activity: Activity, dataSource: DataSource) {
        val dbRef = dataSource.getDatabase().getReference(location)
        val itemsDatabase = dataSource.geItemsDatabase(activity)
        dbRef.keepSynced(true)
        dataSource.getMaterials(activity) { newMaterials ->
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Old_Item>>() {}
                    var oldItems = snapshot.getValue(genericTypeIndicator)
                    if (oldItems != null) {
                        oldItems = oldItems.filter { it != null } as ArrayList<Old_Item>
                        oldItems.forEachIndexed { index, oldItem ->
                            val newItem = Item(index.toLong(), oldItem.itemCode, oldItem.itemName, oldItem.photoUrl,
                                catId.toLong(), arrayListOf(), arrayListOf(), index)
                            oldItem.Materials.forEachIndexed { index, oldMaterial ->
                                if(oldMaterial.Value == "") oldMaterial.Value = "0.0"
                                newItem.itemMaterials.add(ItemMaterial(index.toLong(), getMaterialIdFromName(oldMaterial.MaterialName, newMaterials),
                                    oldMaterial.Value.toDouble(), index))
                            }
                            newItem.priceModifiers.add(PriceModifier(1, "Labour", oldItem.labour.toDouble(), 1))
                            newItem.priceModifiers.add(PriceModifier(1, "Profit", oldItem.profit.toDouble(), 2))
                            newItem.priceModifiers.add(PriceModifier(1, "Dealer Profit", oldItem.dealerProfit.toDouble(), 3))
                            itemsDatabase.child((index+totalItems).toString()).setValue(newItem)
                        }
                        dbRef.push()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })
        }
    }

    private fun importMerryGoRound(activity: Activity, dataSource: DataSource) {
        val dbRef = dataSource.getDatabase().getReference(activity.config.firmId+"/$matsDbVersion/old_data/merry_go_round_old/merry_go_round_old")
        val itemsDatabase = dataSource.geItemsDatabase(activity)
        dbRef.keepSynced(true)
        dataSource.getMaterials(activity) { newMaterials ->
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Old_Item>>() {}
                    var oldItems = snapshot.getValue(genericTypeIndicator)
                    if (oldItems != null) {
                        oldItems = oldItems.filter { it != null } as ArrayList<Old_Item>
                        oldItems.forEachIndexed { index, oldItem ->
                            val newItem = Item(index.toLong(), oldItem.itemCode, oldItem.itemName, oldItem.photoUrl, 5, arrayListOf(), arrayListOf(), index)
                            oldItem.Materials.forEachIndexed { index, oldMaterial ->
                                if(oldMaterial.Value == "") oldMaterial.Value = "0.0"
                                newItem.itemMaterials.add(ItemMaterial(index.toLong(), getMaterialIdFromName(oldMaterial.MaterialName, newMaterials),
                                    oldMaterial.Value.toDouble(), index))
                            }
                            newItem.priceModifiers.add(PriceModifier(1, "Labour", oldItem.labour.toDouble(), 1))
                            newItem.priceModifiers.add(PriceModifier(1, "Profit", oldItem.profit.toDouble(), 2))
                            newItem.priceModifiers.add(PriceModifier(1, "Dealer Profit", oldItem.dealerProfit.toDouble(), 3))
                            itemsDatabase.child((index+19).toString()).setValue(newItem)
                        }
                        dbRef.push()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })
        }
    }

    private fun importSlides(activity: Activity, dataSource: DataSource) {
        val dbRef = dataSource.getDatabase().getReference(activity.config.firmId+"/$matsDbVersion/old_data/slides_old/slides_old")
        val itemsDatabase = dataSource.geItemsDatabase(activity)
        dbRef.keepSynced(true)
        dataSource.getMaterials(activity) { newMaterials ->
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Old_Item>>() {}
                    var oldItems = snapshot.getValue(genericTypeIndicator)
                    if (oldItems != null) {
                        oldItems = oldItems.filter { it != null } as ArrayList<Old_Item>
                        oldItems.forEachIndexed { index, oldItem ->
                            val newItem = Item(index.toLong(), oldItem.itemCode, oldItem.itemName, oldItem.photoUrl, 1, arrayListOf(), arrayListOf(), index)
                            oldItem.Materials.forEachIndexed { index, oldMaterial ->
                                if(oldMaterial.Value == "") oldMaterial.Value = "0.0"
                                newItem.itemMaterials.add(ItemMaterial(index.toLong(), getMaterialIdFromName(oldMaterial.MaterialName, newMaterials),
                                    oldMaterial.Value.toDouble(), index))
                            }
                            newItem.priceModifiers.add(PriceModifier(1, "Labour", oldItem.labour.toDouble(), 1))
                            newItem.priceModifiers.add(PriceModifier(1, "Profit", oldItem.profit.toDouble(), 2))
                            newItem.priceModifiers.add(PriceModifier(1, "Dealer Profit", oldItem.dealerProfit.toDouble(), 3))
                            itemsDatabase.child((index+7).toString()).setValue(newItem)
                        }
                        dbRef.push()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })
        }
    }

    private fun importSwings(activity: Activity, dataSource: DataSource) {
        val dbRef = dataSource.getDatabase().getReference(activity.config.firmId+"/$matsDbVersion/old_data/swings_old")
        val itemsDatabase = dataSource.geItemsDatabase(activity)
        dbRef.keepSynced(true)
        dataSource.getMaterials(activity) { newMaterials ->
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Old_Item>>() {}
                    var oldItems = snapshot.getValue(genericTypeIndicator)
                    if (oldItems != null) {
                        oldItems = oldItems.filter { it != null } as ArrayList<Old_Item>
                        oldItems.forEachIndexed { index, oldItem ->
                            val newItem = Item(index.toLong(), oldItem.itemCode, oldItem.itemName, oldItem.photoUrl, 2, arrayListOf(), arrayListOf(), index)
                            oldItem.Materials.forEachIndexed { index, oldMaterial ->
                                if(oldMaterial.Value == "") oldMaterial.Value = "0.0"
                                newItem.itemMaterials.add(ItemMaterial(index.toLong(), getMaterialIdFromName(oldMaterial.MaterialName, newMaterials),
                                    oldMaterial.Value.toDouble(), index))
                            }
                            newItem.priceModifiers.add(PriceModifier(1, "Labour", oldItem.labour.toDouble(), 1))
                            newItem.priceModifiers.add(PriceModifier(1, "Profit", oldItem.profit.toDouble(), 2))
                            newItem.priceModifiers.add(PriceModifier(1, "Dealer Profit", oldItem.dealerProfit.toDouble(), 3))
                            itemsDatabase.child(index.toString()).setValue(newItem)
                        }
                        dbRef.push()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })
        }
    }

    private fun getMaterialIdFromName(materialName: String, newMaterials: ArrayList<Material>?): String {
        return newMaterials?.find { it.name == materialName }?.id!!
    }

    private fun importCategories(activity: Activity, dataSource: DataSource) {
        val catsDatabase = dataSource.getCatsDatabase(activity)
        catsDatabase.keepSynced(true)
        val categories = arrayListOf( "Slides",
            "Swings",
            "Climbers",
            "See Saws",
            "Merry Go Round",
            "Exercise Range",
            "Garden Range",
            "MultiPlaySystem")
        var count = 1L
        categories.forEach {
            catsDatabase.child("cat_" + count.toString()).setValue(
                Category("cat_" + count, it, count)
            )
            count++
        }
        catsDatabase.push()
    }

    private fun importMaterials(activity: Activity, dataSource: DataSource) {
        val dbRef = dataSource.getDatabase().getReference(activity.config.firmId+"/$matsDbVersion/old_data/materials_old")
        val matsDatabase = dataSource.getMatsDatabase(activity)
        dbRef.keepSynced(true)

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Old_Material>>() {}
                var currentMats = snapshot.getValue(genericTypeIndicator)
                if (currentMats != null) {
                    currentMats = currentMats.filter { it != null } as ArrayList<Old_Material>
                    var count = 1L
                    currentMats.forEach {
                        matsDatabase.child("mat_" + count.toString()).setValue(
                            Material(
                                "mat_" + count,
                                it.MaterialName,
                                it.Unit,
                                it.UnitPrice.toDouble(),
                                count
                            )
                        )
                        count++
                    }
                    dbRef.push()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }
}
