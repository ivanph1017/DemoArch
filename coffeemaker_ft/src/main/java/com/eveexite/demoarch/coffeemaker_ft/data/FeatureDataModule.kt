package com.eveexite.demoarch.coffeemaker_ft.data

import android.content.Context
import com.eveexite.demoarch.coffeemaker_ft.FeatureScope
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRestService
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSourceFactory
import com.eveexite.demoarch.coffeemaker_ft.data.rest.dispatcher.RestDispatcherFactory
import com.eveexite.demoarch.coffeemaker_ft.data.util.JsonUtil
import com.eveexite.demoarch.core.data.CoreRestServerFactory
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides

@Module
class FeatureDataModule {

    @FeatureScope
    @Provides
    fun provideObjectMapper() = ObjectMapper()

    @FeatureScope
    @Provides
    fun provideJsonUtil(
        context: Context,
        objectMapper: ObjectMapper
    ) =
        JsonUtil(
            context,
            objectMapper
        )

    @FeatureScope
    @Provides
    fun provideRestDispatcherFactory(jsonUtil: JsonUtil) = RestDispatcherFactory(jsonUtil)

    @FeatureScope
    @Provides
    fun provideCoffeeMakerDataSourceFactory(
        coffeeMakerRestService: CoffeeMakerRestService,
        coreRestServerFactory: CoreRestServerFactory,
        restDispatcherFactory: RestDispatcherFactory,
        port: Int
    ) =
        CoffeeMakerDataSourceFactory(
            coffeeMakerRestService,
            coreRestServerFactory,
            restDispatcherFactory,
            port
        )
}