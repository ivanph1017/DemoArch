package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource

import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataGetterEventListener
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataPosterEventListener
import java.lang.ref.WeakReference

interface CoffeeMakerDataSource {

    fun getSingleCoffeeMakerEntity(weakListener: WeakReference<DataGetterEventListener>)

    fun getCoffeeMakerEntity(weakListener: WeakReference<DataGetterEventListener>)

    fun turnOnCoffeeMakerEntity(
        weakListener: WeakReference<DataPosterEventListener>,
        turnOn: Boolean
    )
}

