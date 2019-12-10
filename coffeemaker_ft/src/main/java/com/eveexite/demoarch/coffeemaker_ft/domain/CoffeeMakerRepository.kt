package com.eveexite.demoarch.coffeemaker_ft.domain

import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataGetterEventListener
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataPosterEventListener
import java.lang.ref.WeakReference

interface CoffeeMakerRepository {

    fun getSingleEntity(
        weakListener: WeakReference<DataGetterEventListener>
    )

    fun getEntity(
        weakListener: WeakReference<DataGetterEventListener>
    )

    fun turnOn(
        weakListener: WeakReference<DataPosterEventListener>,
        turnOn: Boolean
    )
}