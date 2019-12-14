package com.eveexite.demoarch.coffeemaker_ft.data

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRestService
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSourceFactory
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSourceFactoryImpl
import com.eveexite.demoarch.coffeemaker_ft.data.rest.RestDispatcher
import com.eveexite.demoarch.core.device.JsonUtil
import com.eveexite.demoarch.core.FeatureScope
import com.eveexite.demoarch.core.data.RestServer
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.Dispatcher

@Module
open class FeatureDataModule {

    @FeatureScope
    @Provides
    fun provideDispatcher(jsonUtil: JsonUtil): Dispatcher = RestDispatcher(jsonUtil)

    protected open fun createCoffeeMakerDataSourceFactory(
        coffeeMakerRestService: CoffeeMakerRestService,
        restServerBuilder: RestServer.Builder,
        dispatcher: Dispatcher,
        port: Int
    ): CoffeeMakerDataSourceFactory =
        CoffeeMakerDataSourceFactoryImpl(
            coffeeMakerRestService,
            restServerBuilder,
            dispatcher,
            port
        )

    @FeatureScope
    @Provides
    fun provideCoffeeMakerDataSourceFactory(
        coffeeMakerRestService: CoffeeMakerRestService,
        restServerBuilder: RestServer.Builder,
        dispatcher: Dispatcher,
        port: Int
    ): CoffeeMakerDataSourceFactory =
        createCoffeeMakerDataSourceFactory(coffeeMakerRestService,
            restServerBuilder,
            dispatcher,
            port
        )
}