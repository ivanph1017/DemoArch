package com.eveexite.demoarch.coffeemaker_ft.domain.usecase

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity.CoffeeMakerEntity
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataGetterEventListener
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataPosterEventListener
import com.eveexite.demoarch.coffeemaker_ft.domain.CoffeeMakerRepository
import com.eveexite.demoarch.coffeemaker_ft.domain.listener.DomainSwitchEventListener
import com.eveexite.demoarch.coffeemaker_ft.domain.mapper.MapToCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.domain.model.CoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.domain.model.Status
import java.lang.ref.WeakReference

class TurnOnCoffeeMaker(
    private val repository: CoffeeMakerRepository,
    private val mapper: MapToCoffeeMaker
): FeatureBaseUseCase<DomainSwitchEventListener>(), DataGetterEventListener, DataPosterEventListener {

    override fun getCodeBlock(): Runnable =
        Runnable {
            val weakThis: WeakReference<DataGetterEventListener> = WeakReference(this)
            repository.getSingleEntity(weakThis)
        }

    override fun onDataChange(coffeeMakerEntity: CoffeeMakerEntity) {
        val coffeeMaker: CoffeeMaker = mapper.map(coffeeMakerEntity)
        val weakThis: WeakReference<DataPosterEventListener> = WeakReference(this)
        when (coffeeMaker.status) {
            Status.COFFEE_MAKER_READY -> {
                repository.turnOn(weakThis,true)
            }
            Status.COFFEE_MAKER_UNPLUGGED,
            Status.NOT_ENOUGH_WATER,
            Status.COFFEE_MAKER_RESTING_1,
            Status.COFFEE_MAKER_RESTING_2,
            Status.COFFEE_MAKER_RESTING_3,
            Status.COFFEE_MAKER_RESTING_4,
            Status.COFFEE_MAKER_RESTING_5 -> {
                val e = Exception(coffeeMaker.status.text)
                onError(e)
            }
            Status.COFFEE_READY, Status.PREPARING_COFFEE -> {
                repository.turnOn(weakThis, false)
            }
        }
    }

    override fun onSuccess() {
        weakListener.get()?.onCoffeeMakerTurnedOn()
    }
}