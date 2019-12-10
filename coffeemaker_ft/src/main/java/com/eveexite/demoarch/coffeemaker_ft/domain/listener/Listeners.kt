package com.eveexite.demoarch.coffeemaker_ft.domain.listener

import com.eveexite.demoarch.coffeemaker_ft.domain.model.CoffeeMaker
import com.eveexite.demoarch.core.domain.DomainErrorEventListener

interface DomainCheckerEventListener :
    DomainErrorEventListener {

    fun onActionMessageReceived(actionMessage: String)
}

interface DomainGetterEventListener :
    DomainErrorEventListener {

    fun onCoffeeMakerObtained(coffeeMaker: CoffeeMaker)
}

interface DomainSwitchEventListener :
    DomainErrorEventListener {

    fun onCoffeeMakerTurnedOn()
}