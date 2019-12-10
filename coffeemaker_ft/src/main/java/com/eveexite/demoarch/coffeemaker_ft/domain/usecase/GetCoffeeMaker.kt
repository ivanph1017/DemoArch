package com.eveexite.demoarch.coffeemaker_ft.domain.usecase

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity.CoffeeMakerEntity
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataGetterEventListener
import com.eveexite.demoarch.coffeemaker_ft.domain.CoffeeMakerRepository
import com.eveexite.demoarch.coffeemaker_ft.domain.listener.DomainGetterEventListener
import com.eveexite.demoarch.coffeemaker_ft.domain.mapper.MapToCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.domain.model.CoffeeMaker
import java.lang.ref.WeakReference

class GetCoffeeMaker(
    private val repository: CoffeeMakerRepository,
    private val mapper: MapToCoffeeMaker
): FeatureBaseUseCase<DomainGetterEventListener>(), DataGetterEventListener {

    override fun getCodeBlock() = Runnable {
        val weakThis: WeakReference<DataGetterEventListener> = WeakReference(this)
        repository.getEntity(weakThis)
    }

    override fun onDataChange(coffeeMakerEntity: CoffeeMakerEntity) {
        val coffeeMaker: CoffeeMaker = mapper.map(coffeeMakerEntity)
        weakListener.get()?.onCoffeeMakerObtained(coffeeMaker)
    }
}