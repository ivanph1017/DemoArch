package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRestService
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.impl.CoffeeMakerDataSourceFirebaseImpl
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.impl.CoffeeMakerDataSourceRestImpl
import com.eveexite.demoarch.core.data.RestServer
import okhttp3.mockwebserver.Dispatcher

class CoffeeMakerDataSourceFactoryImpl(
    private val coffeeMakerRestService: CoffeeMakerRestService,
    private val restServerBuilder: RestServer.Builder,
    private val dispatcher: Dispatcher,
    private val port: Int
): CoffeeMakerDataSourceFactory {

    override fun createDataSource(isFirebase: Boolean): CoffeeMakerDataSource =
        if (isFirebase)
            CoffeeMakerDataSourceFirebaseImpl()
        else
            CoffeeMakerDataSourceRestImpl(
                coffeeMakerRestService,
                restServerBuilder,
                dispatcher,
                port
            )
}