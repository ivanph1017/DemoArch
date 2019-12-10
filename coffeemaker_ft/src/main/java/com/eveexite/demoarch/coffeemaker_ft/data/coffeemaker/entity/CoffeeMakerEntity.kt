package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity

data class CoffeeMakerEntity(
    var turnOn : Boolean = false,
    var coffeeReady : Boolean = false,
    var coffeeMakerReady : Boolean = false,
    var timer : String = "00:00",
    var timerSleep : Int = 5,
    var waterLevel : Int = 0,
    var status: CoffeeMakerNetStatusEntity = CoffeeMakerNetStatusEntity()
)