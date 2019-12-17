package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSource
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSourceFactory
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataGetterEventListener
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataPosterEventListener
import com.eveexite.demoarch.coffeemaker_ft.domain.CoffeeMakerRepository
import com.eveexite.demoarch.core.device.DeviceInfoUtil
import java.lang.ref.WeakReference

class CoffeeMakerRepositoryImpl(
    dataSourceFactory: CoffeeMakerDataSourceFactory,
    private val deviceInfoUtil: DeviceInfoUtil
): CoffeeMakerRepository {

    private val firebaseImpl: CoffeeMakerDataSource = dataSourceFactory.createDataSource(true)
    private val restImpl: CoffeeMakerDataSource = dataSourceFactory.createDataSource(false)

    override fun getSingleEntity(
        weakListener: WeakReference<DataGetterEventListener>
    ) {
        if (deviceInfoUtil.isOnline) {
            restImpl.getSingleCoffeeMakerEntity(weakListener)
        } else {
            firebaseImpl.getSingleCoffeeMakerEntity(weakListener)
        }
    }

    override fun getEntity(
        weakListener: WeakReference<DataGetterEventListener>
    ) {
        if (deviceInfoUtil.isOnline) {
            restImpl.getCoffeeMakerEntity(weakListener)
        } else {
            firebaseImpl.getCoffeeMakerEntity(weakListener)
        }
    }

    override fun turnOn(
        weakListener: WeakReference<DataPosterEventListener>,
        turnOn: Boolean
    ) {
        if (deviceInfoUtil.isOnline) {
            restImpl.turnOnCoffeeMakerEntity(weakListener, turnOn)
        } else {
            firebaseImpl.turnOnCoffeeMakerEntity(weakListener, turnOn)
        }
    }
}