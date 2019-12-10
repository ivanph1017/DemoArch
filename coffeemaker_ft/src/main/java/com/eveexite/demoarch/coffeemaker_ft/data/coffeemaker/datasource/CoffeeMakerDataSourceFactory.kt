package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRestService
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.impl.CoffeeMakerDataSourceFirebaseImpl
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.impl.CoffeeMakerDataSourceRestImpl
import com.eveexite.demoarch.coffeemaker_ft.data.rest.dispatcher.RestDispatcherFactory
import com.eveexite.demoarch.core.data.CoreRestServerFactory

class CoffeeMakerDataSourceFactory(
    private val coffeeMakerRestService: CoffeeMakerRestService,
    private val coreRestServerFactory: CoreRestServerFactory,
    private val restDispatcherFactory: RestDispatcherFactory,
    private val port: Int
) {

    fun createDataSource(isFirebase: Boolean): CoffeeMakerDataSource =
        if (isFirebase)
            CoffeeMakerDataSourceFirebaseImpl()
        else
            CoffeeMakerDataSourceRestImpl(
                coffeeMakerRestService,
                coreRestServerFactory,
                restDispatcherFactory,
                port
            )
}