package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity

data class CoffeeMakerNetStatusEntity(
    var lastChanged: Long = 0,
    var state: String = "offline"
)