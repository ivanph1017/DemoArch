package com.eveexite.demoarch.coffeemaker_ft.domain.mapper

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity.CoffeeMakerEntity
import com.eveexite.demoarch.coffeemaker_ft.domain.model.CoffeeMaker

class MapToCoffeeMaker(private val mapper: MapToCoffeeMakerNetStatus) {

    fun map(entity: CoffeeMakerEntity) =
        CoffeeMaker(
            entity.turnOn,
            entity.coffeeReady,
            entity.coffeeMakerReady,
            entity.timer,
            entity.timerSleep,
            entity.waterLevel,
            mapper.map(entity.status)
        )
}