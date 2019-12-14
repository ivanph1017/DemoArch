package com.eveexite.demoarch.coffeemaker_ft.domain

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRepositoryImpl
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSourceFactory
import com.eveexite.demoarch.coffeemaker_ft.domain.mapper.MapToCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.domain.mapper.MapToCoffeeMakerNetStatus
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.CheckCoffeeMakerSwitch
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.GetCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.TurnOnCoffeeMaker
import com.eveexite.demoarch.core.FeatureScope
import com.eveexite.demoarch.core.device.DeviceInfoUtil
import dagger.Module
import dagger.Provides

@Module
class FeatureDomainModule {

    @FeatureScope
    @Provides
    fun provideCoffeeMakerRepository(
        dataSourceFactory: CoffeeMakerDataSourceFactory,
        deviceInfoUtil: DeviceInfoUtil
    ): CoffeeMakerRepository =
        CoffeeMakerRepositoryImpl(
            dataSourceFactory,
            deviceInfoUtil
        )
    @FeatureScope
    @Provides
    fun provideMapToCoffeeMakerNetStatus() = MapToCoffeeMakerNetStatus()

    @FeatureScope
    @Provides
    fun provideMapToCoffeeMaker(mapper: MapToCoffeeMakerNetStatus) = MapToCoffeeMaker(mapper)

    @FeatureScope
    @Provides
    fun provideCheckCoffeeMakerSwitch(
        coffeeMakerRepository: CoffeeMakerRepository,
        mapToCoffeeMaker: MapToCoffeeMaker
    ) = CheckCoffeeMakerSwitch(coffeeMakerRepository, mapToCoffeeMaker)

    @FeatureScope
    @Provides
    fun provideGetCoffeeMaker(
        coffeeMakerRepository: CoffeeMakerRepository,
        mapToCoffeeMaker: MapToCoffeeMaker
    ) = GetCoffeeMaker(coffeeMakerRepository, mapToCoffeeMaker)

    @FeatureScope
    @Provides
    fun provideTurnOnCoffeeMaker(
        coffeeMakerRepository: CoffeeMakerRepository,
        mapToCoffeeMaker: MapToCoffeeMaker
    ) = TurnOnCoffeeMaker(coffeeMakerRepository, mapToCoffeeMaker)
}