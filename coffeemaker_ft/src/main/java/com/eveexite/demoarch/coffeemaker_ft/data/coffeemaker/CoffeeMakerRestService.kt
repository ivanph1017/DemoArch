package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker

import com.eveexite.demoarch.coffeemaker_ft.BuildConfig
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity.CoffeeMakerEntity
import retrofit2.Call
import retrofit2.http.GET

interface CoffeeMakerRestService {

    @GET(BuildConfig.COFFEE_MAKER)
    fun getCoffeeMakerEntity(): Call<CoffeeMakerEntity>
}