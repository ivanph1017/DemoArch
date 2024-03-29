package com.eveexite.demoarch.coffeemaker_ft.data.rest

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRestService
import com.eveexite.demoarch.core.FeatureScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RestModule {

    @FeatureScope
    @Provides
    fun provideCoffeeMakerRestService(retrofit: Retrofit): CoffeeMakerRestService
            = retrofit.create(CoffeeMakerRestService::class.java)
}