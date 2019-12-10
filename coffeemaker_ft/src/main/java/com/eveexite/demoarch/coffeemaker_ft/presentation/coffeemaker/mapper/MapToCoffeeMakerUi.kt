package com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.mapper

import com.eveexite.demoarch.coffeemaker_ft.domain.model.CoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.model.CoffeeMakerUi
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.model.InfoUi

class MapToCoffeeMakerUi(private val mapper: MapToStatusUi) {

    fun map(model: CoffeeMaker): CoffeeMakerUi {
        return CoffeeMakerUi(
            model.turnOn,
            InfoUi(model.timer, model.timerSleep, model.waterLevel),
            mapper.map(model.status)
        )
    }
}