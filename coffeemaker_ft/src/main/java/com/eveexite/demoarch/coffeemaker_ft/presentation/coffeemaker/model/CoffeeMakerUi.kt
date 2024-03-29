package com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.model

data class CoffeeMakerUi(
    var turnOn: Boolean,
    var infoUi: InfoUi,
    var statusUi: StatusUi
) {

    var animUi: AnimUi = AnimUi(makeFileUri(), playAnimation())

    private fun makeFileUri(): String {
        return when (statusUi) {
            StatusUi.COFFEE_READY -> "coffee_cup.json"
            else -> "coffee_maker.json"
        }
    }

    private fun playAnimation(): Boolean {
        return when (statusUi) {
            StatusUi.COFFEE_READY, StatusUi.PREPARING_COFFEE -> true
            else -> false
        }
    }

}