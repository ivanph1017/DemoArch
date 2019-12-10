package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.impl

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSource
import com.eveexite.demoarch.coffeemaker_ft.data.firebase.exception.FirebaseDataCastException
import com.eveexite.demoarch.coffeemaker_ft.data.firebase.exception.FirebaseDataException
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity.CoffeeMakerEntity
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataGetterEventListener
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataPosterEventListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import java.lang.ref.WeakReference

class CoffeeMakerDataSourceFirebaseImpl :
    CoffeeMakerDataSource {

    init {
        FirebaseDatabase.getInstance().goOnline()
    }

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.root

    override fun getSingleCoffeeMakerEntity(
        weakListener: WeakReference<DataGetterEventListener>
    ) {
        val query: Query = databaseReference
        query.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val entity: CoffeeMakerEntity = dataSnapshot.getValue(
                        CoffeeMakerEntity::class.java
                    )
                        ?: throw FirebaseDataCastException(
                            "unable to cast firebase data response to ${CoffeeMakerEntity::class.simpleName}"
                        )
                    weakListener.get()?.onDataChange(entity)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    val exception =
                        FirebaseDataException(
                            databaseError
                        )
                    weakListener.get()?.onError(exception)

                }
            }
        )
    }

    override fun getCoffeeMakerEntity(
        weakListener: WeakReference<DataGetterEventListener>
    ) {
        val query: Query = databaseReference
        query.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val entity: CoffeeMakerEntity = dataSnapshot.getValue(
                        CoffeeMakerEntity::class.java
                    )
                        ?: throw FirebaseDataCastException(
                            "unable to cast firebase data response to ${CoffeeMakerEntity::class.simpleName}"
                        )
                    weakListener.get()?.onDataChange(entity) ?: query.removeEventListener(this)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    val exception =
                        FirebaseDataException(
                            databaseError
                        )
                    weakListener.get()?.onError(exception) ?: query.removeEventListener(this)
                }
            }
        )
    }

    override fun turnOnCoffeeMakerEntity(
        weakListener: WeakReference<DataPosterEventListener>,
        turnOn: Boolean
    ) {
        val task: Task<Void> = databaseReference.child("turnOn").setValue(turnOn)
        task.addOnSuccessListener { weakListener.get()?.onSuccess() }
        task.addOnFailureListener { weakListener.get()?.onError(it) }
    }
}