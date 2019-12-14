package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRestService
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.impl.CoffeeMakerDataSourceRestImplTest

class CoffeeMakerDataSourceFactoryTest(
    private val coffeeMakerRestService: CoffeeMakerRestService
): CoffeeMakerDataSourceFactory {

    override fun createDataSource(isFirebase: Boolean): CoffeeMakerDataSource =
        CoffeeMakerDataSourceRestImplTest(
            coffeeMakerRestService
        )
}