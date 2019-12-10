package com.eveexite.demoarch.coffeemaker_ft.domain.usecase

import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataErrorEventListener
import com.eveexite.demoarch.core.domain.CoreBaseUseCase
import com.eveexite.demoarch.core.domain.DomainErrorEventListener

abstract class FeatureBaseUseCase<T: DomainErrorEventListener>
    : CoreBaseUseCase<T>(), DataErrorEventListener {

    override fun onError(e: Exception) {
        weakListener.get()?.onError(e)
    }
}