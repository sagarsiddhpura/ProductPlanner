package com.siddworks.productplanner.data

import android.app.Activity
import com.google.firebase.database.*
import com.siddworks.productplanner.BuildConfig
import com.siddworks.productplanner.extensions.config

private var db: FirebaseDatabase? = null

var matsDbVersion: String
    get() = if(BuildConfig.DEBUG)  "VT1" else "VR1"
    set(value) = TODO()
private var catsDbVersion: String
    get() = if(BuildConfig.DEBUG)  "VT1" else "VR1"
    set(value) = TODO()
private var purchaseLogDbVersion: String
    get() = if(BuildConfig.DEBUG)  "VT1" else "VR1"
    set(value) = TODO()

class DataSource {

    private var materials: ArrayList<Material>? = null
    private var categories: ArrayList<Category>? = null

    fun getDatabase(): FirebaseDatabase {
        if (db == null) {
            db = FirebaseDatabase.getInstance()
            (db as FirebaseDatabase).setPersistenceEnabled(true)
        }

        return db!!
    }

    fun getMatsDatabase(activity: Activity): DatabaseReference {
        return getDatabase().getReference(activity.config.firmId+"/$matsDbVersion/materials")
    }

    fun getMaterials(activity: Activity, callback: (mats: ArrayList<Material>?) -> Unit) {
        if (materials != null) {
            callback(materials!!)
            return
        } else {
            val database = getMatsDatabase(activity)
            database.keepSynced(true)

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val genericTypeIndicator = object : GenericTypeIndicator<HashMap<String, Material>>() {}
                    val currentMatsMap = snapshot.getValue(genericTypeIndicator)
                    if (currentMatsMap != null) {
                        var currentMats = currentMatsMap.values
                        currentMats = currentMats.filter { it != null } as ArrayList<Material>
                        materials = currentMats
                        callback(materials)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val genericTypeIndicator = object : GenericTypeIndicator<HashMap<String, Material>>() {}
                    val currentMatsMap = snapshot.getValue(genericTypeIndicator)
                    if (currentMatsMap != null) {
                        var currentMats = currentMatsMap.values
                        currentMats = currentMats!!.filter { it != null } as ArrayList<Material>
                        materials = currentMats as ArrayList<Material>
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + (databaseError?.code ?: ""))
                }
            })
        }
    }

    fun addMaterial(activity: Activity, material: Material) {
        val matsDatabase = getMatsDatabase(activity)
        matsDatabase.child(material.id).setValue(material)
    }

    fun removeMaterial(activity: Activity, material: Material) {
        val matsDatabase = getMatsDatabase(activity)
        matsDatabase.child(material.id).removeValue()
    }

    fun updateMaterial(activity: Activity, material: Material) {
        val matsDatabase = getMatsDatabase(activity)
        matsDatabase.child(material.id).setValue(material)
    }

    fun addCategory(activity: Activity, category: Category) {
        val catsDatabase = getCatsDatabase(activity)
        catsDatabase.child(category.id).setValue(category)
    }

    fun removeCategory(activity: Activity, category: Category) {
        val catsDatabase = getCatsDatabase(activity)
        catsDatabase.child(category.id).removeValue()
    }

    fun updateCategory(activity: Activity, category: Category) {
        val catsDatabase = getCatsDatabase(activity)
        catsDatabase.child(category.id).setValue(category)
    }

    fun getCatsDatabase(activity: Activity): DatabaseReference {
        return getDatabase().getReference(activity.config.firmId+"/$catsDbVersion/categories")
    }

    fun geItemsDatabase(activity: Activity): DatabaseReference {
        return getDatabase().getReference(activity.config.firmId+"/$catsDbVersion/items")
    }

    fun getCategories(activity: Activity, callback: (cats: ArrayList<Category>?) -> Unit) {
        if (categories != null) {
            callback(categories!!)
            return
        } else {
            val database = getCatsDatabase(activity)
            database.keepSynced(true)

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val genericTypeIndicator = object : GenericTypeIndicator<HashMap<String, Category>>() {}
                    val currentItemsMap = snapshot.getValue(genericTypeIndicator)
                    if (currentItemsMap != null) {
                        var currentItems = currentItemsMap.values
                        currentItems = currentItems.filter { it != null } as ArrayList<Category>
                        categories = currentItems
                        callback(categories)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val genericTypeIndicator = object : GenericTypeIndicator<HashMap<String, Category>>() {}
                    val currentItemsMap = snapshot.getValue(genericTypeIndicator)
                    if (currentItemsMap != null) {
                        var currentItems = currentItemsMap.values
                        currentItems = currentItems.filter { it != null } as ArrayList<Category>
                        categories = currentItems as ArrayList<Category>
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + (databaseError?.code ?: ""))
                }
            })
        }
    }

}