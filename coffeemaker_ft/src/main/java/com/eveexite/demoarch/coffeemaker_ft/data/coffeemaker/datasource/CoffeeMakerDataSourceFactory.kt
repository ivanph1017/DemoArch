package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRestService
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.impl.CoffeeMakerDataSourceFirebaseImpl
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.impl.CoffeeMakerDataSourceRestImpl

class CoffeeMakerDataSourceFactory(
    private val coffeeMakerRestService: CoffeeMakerRestService
) {

    fun createDataSource(isFirebase: Boolean): CoffeeMakerDataSource =
        if (isFirebase)
            CoffeeMakerDataSourceFirebaseImpl()
        else
            CoffeeMakerDataSourceRestImpl(coffeeMakerRestService)
}