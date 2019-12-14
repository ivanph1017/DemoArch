package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource

interface CoffeeMakerDataSourceFactory {

    fun createDataSource(isFirebase: Boolean): CoffeeMakerDataSource
}