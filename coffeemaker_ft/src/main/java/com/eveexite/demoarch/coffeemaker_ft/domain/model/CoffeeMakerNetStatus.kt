package com.eveexite.demoarch.coffeemaker_ft.domain.model

data class CoffeeMakerNetStatus(
    var lastChanged: Long = 0,
    var state: String = "offline"
)