package com.eveexite.demoarch.coffeemaker_ft.presentation

import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.CheckCoffeeMakerSwitch
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.GetCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.TurnOnCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.mapper.MapToCoffeeMakerUi
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.mapper.MapToStatusUi
import com.eveexite.demoarch.core.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class FeatureUiModule {

    @FeatureScope
    @Provides
    fun provideMapToStatusUi(): MapToStatusUi {
        return MapToStatusUi()
    }

    @FeatureScope
    @Provides
    fun provideMapToCoffeeMakerUi(mapToStatusUi: MapToStatusUi): MapToCoffeeMakerUi {
        return MapToCoffeeMakerUi(mapToStatusUi)
    }

    @FeatureScope
    @Provides
    fun provideViewModelFactory(
        checkCoffeeMakerSwitch: CheckCoffeeMakerSwitch,
        getCoffeeMaker: GetCoffeeMaker,
        turnOnCoffeeMaker: TurnOnCoffeeMaker,
        mapToCoffeeMakerUi: MapToCoffeeMakerUi
    ) =
        ViewModelFactory(
            checkCoffeeMakerSwitch,
            getCoffeeMaker,
            turnOnCoffeeMaker,
            mapToCoffeeMakerUi
        )
}