package com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.mapper

import com.eveexite.demoarch.coffeemaker_ft.domain.model.Status
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.model.StatusUi

class MapToStatusUi {

    fun map(model: Status): StatusUi {
        return when (model) {
            Status.COFFEE_MAKER_UNPLUGGED -> StatusUi.COFFEE_MAKER_UNPLUGGED
            Status.COFFEE_MAKER_READY -> StatusUi.COFFEE_MAKER_READY
            Status.NOT_ENOUGH_WATER -> StatusUi.NOT_ENOUGH_WATER
            Status.COFFEE_READY -> StatusUi.COFFEE_READY
            Status.PREPARING_COFFEE -> StatusUi.PREPARING_COFFEE
            Status.COFFEE_MAKER_RESTING_1 -> StatusUi.COFFEE_MAKER_RESTING_1
            Status.COFFEE_MAKER_RESTING_2 -> StatusUi.COFFEE_MAKER_RESTING_2
            Status.COFFEE_MAKER_RESTING_3 -> StatusUi.COFFEE_MAKER_RESTING_3
            Status.COFFEE_MAKER_RESTING_4 -> StatusUi.COFFEE_MAKER_RESTING_4
            Status.COFFEE_MAKER_RESTING_5 -> StatusUi.COFFEE_MAKER_RESTING_5
        }
    }
}