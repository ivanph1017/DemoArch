package com.eveexite.demoarch.coffeemaker_ft.domain.mapper

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity.CoffeeMakerNetStatusEntity
import com.eveexite.demoarch.coffeemaker_ft.domain.model.CoffeeMakerNetStatus

class MapToCoffeeMakerNetStatus {

    fun map(entity: CoffeeMakerNetStatusEntity) =
        CoffeeMakerNetStatus(
            entity.lastChanged,
            entity.state
        )
}