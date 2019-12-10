package com.eveexite.demoarch.coffeemaker_ft.data.listener

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity.CoffeeMakerEntity

interface DataErrorEventListener {

    fun onError(e: Exception)
}

interface DataGetterEventListener : DataErrorEventListener {

    fun onDataChange(coffeeMakerEntity: CoffeeMakerEntity)
}

interface DataPosterEventListener : DataErrorEventListener {

    fun onSuccess()
}