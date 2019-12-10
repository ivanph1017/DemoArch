package com.eveexite.demoarch.coffeemaker_ft.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.CheckCoffeeMakerSwitch
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.GetCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.TurnOnCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.mapper.MapToCoffeeMakerUi
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.viewmodel.ActionViewModel
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.viewmodel.CoffeeMakerViewModel
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.viewmodel.StatusViewModel

class ViewModelFactory(
    private val checkCoffeeMakerSwitch: CheckCoffeeMakerSwitch,
    private val getCoffeeMaker: GetCoffeeMaker,
    private val turnOnCoffeeMaker: TurnOnCoffeeMaker,
    private val mapper: MapToCoffeeMakerUi
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoffeeMakerViewModel::class.java)) {
            return CoffeeMakerViewModel(getCoffeeMaker, mapper) as T
        }
        if (modelClass.isAssignableFrom(ActionViewModel::class.java)) {
            return ActionViewModel(checkCoffeeMakerSwitch) as T
        }
        if (modelClass.isAssignableFrom(StatusViewModel::class.java)) {
            return StatusViewModel(turnOnCoffeeMaker) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}