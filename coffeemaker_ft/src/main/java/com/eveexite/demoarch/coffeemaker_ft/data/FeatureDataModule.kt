package com.eveexite.demoarch.coffeemaker_ft.data

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRestService
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSourceFactory
import com.eveexite.demoarch.core.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class FeatureDataModule {

    @FeatureScope
    @Provides
    fun provideCoffeeMakerDataSourceFactory(
        coffeeMakerRestService: CoffeeMakerRestService
    ): CoffeeMakerDataSourceFactory =
        CoffeeMakerDataSourceFactory(coffeeMakerRestService)
}