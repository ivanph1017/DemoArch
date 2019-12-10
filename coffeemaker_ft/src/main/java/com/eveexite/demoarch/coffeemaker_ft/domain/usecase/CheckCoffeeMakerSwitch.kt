package com.eveexite.demoarch.coffeemaker_ft.domain.usecase

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity.CoffeeMakerEntity
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataGetterEventListener
import com.eveexite.demoarch.coffeemaker_ft.domain.CoffeeMakerRepository
import com.eveexite.demoarch.coffeemaker_ft.domain.listener.DomainCheckerEventListener
import com.eveexite.demoarch.coffeemaker_ft.domain.mapper.MapToCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.domain.model.CoffeeMaker
import java.lang.ref.WeakReference

private const val WANT_TO_TURN_OFF: String = "¿Deseas apagar la cafetera?"
private const val WANT_TO_TURN_ON: String = "¿Deseas encender la cafetera?"

class CheckCoffeeMakerSwitch(
    private val repository: CoffeeMakerRepository,
    private val mapper: MapToCoffeeMaker
) : FeatureBaseUseCase<DomainCheckerEventListener>(), DataGetterEventListener {

    override fun getCodeBlock(): Runnable =
        Runnable {
            val weakThis: WeakReference<DataGetterEventListener> = WeakReference(this)
            repository.getSingleEntity(weakThis)
        }

    override fun onDataChange(coffeeMakerEntity: CoffeeMakerEntity) {
        val coffeeMaker: CoffeeMaker = mapper.map(coffeeMakerEntity)
        val actionMessage: String = if (coffeeMaker.turnOn) WANT_TO_TURN_OFF else WANT_TO_TURN_ON
        weakListener.get()?.onActionMessageReceived(actionMessage)
    }
}