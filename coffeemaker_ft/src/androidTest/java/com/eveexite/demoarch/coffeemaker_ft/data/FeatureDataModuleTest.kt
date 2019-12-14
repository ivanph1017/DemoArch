package com.eveexite.demoarch.coffeemaker_ft.data

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRestService
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSourceFactory
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSourceFactoryTest
import com.eveexite.demoarch.core.data.RestServer
import dagger.Module
import okhttp3.mockwebserver.Dispatcher

@Module
class FeatureDataModuleTest: FeatureDataModule() {

    override fun createCoffeeMakerDataSourceFactory(
        coffeeMakerRestService: CoffeeMakerRestService,
        restServerBuilder: RestServer.Builder,
        dispatcher: Dispatcher,
        port: Int
    ): CoffeeMakerDataSourceFactory = CoffeeMakerDataSourceFactoryTest(coffeeMakerRestService)
}