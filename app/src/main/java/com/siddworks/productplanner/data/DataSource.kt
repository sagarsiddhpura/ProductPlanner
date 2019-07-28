package com.siddworks.productplanner.data

import android.app.Activity
import com.google.firebase.database.*
import com.siddworks.productplanner.BuildConfig
import com.siddworks.productplanner.extensions.config
import com.siddworks.productplanner.materials.ManageMaterialsActivity

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

    fun getMaterials(activity: Activity, callback: (cats: ArrayList<Material>?) -> Unit) {
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
        val catsDatabase = getMatsDatabase(activity)
        catsDatabase.child(material.id.toString()).setValue(material)
    }

    fun removeMaterial(activity: Activity, material: Material) {
        val catsDatabase = getMatsDatabase(activity)
        catsDatabase.child(material.id.toString()).removeValue()
    }

    fun getCatsDatabase(activity: Activity): DatabaseReference {
        return getDatabase().getReference(activity.config.firmId+"/$catsDbVersion/categories")
    }

    fun geItemsDatabase(activity: Activity): DatabaseReference {
        return getDatabase().getReference(activity.config.firmId+"/$catsDbVersion/items")
    }

    fun updateMaterial(activity: Activity, material: Material) {
        val catsDatabase = getMatsDatabase(activity)
        catsDatabase.child(material.id.toString()).setValue(material)
    }

}