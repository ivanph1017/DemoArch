package com.eveexite.demoarch.coffeemaker_ft

import com.eveexite.demoarch.coffeemaker_ft.data.FeatureDataModule
import com.eveexite.demoarch.coffeemaker_ft.data.rest.RestModule
import com.eveexite.demoarch.coffeemaker_ft.domain.FeatureDomainModule
import com.eveexite.demoarch.coffeemaker_ft.presentation.FeatureUiModule
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.view.CoffeeMakerActivity
import com.eveexite.demoarch.core.CoreComponent
import com.eveexite.demoarch.core.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [
        RestModule::class,
        FeatureDataModule::class,
        FeatureDomainModule::class,
        FeatureUiModule::class
    ],
    dependencies = [
        CoreComponent::class
    ]
)
interface FeatureComponent {

    fun inject(coffeeMakerActivity: CoffeeMakerActivity)

    @Component.Factory
    interface Factory {

        fun create(coreComponent: CoreComponent): FeatureComponent
    }
}