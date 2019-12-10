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
            firebaseImpl.getSingleCoffeeMakerEntity(weakListener)
        } else {
            restImpl.getSingleCoffeeMakerEntity(weakListener)
        }
    }

    override fun getEntity(
        weakListener: WeakReference<DataGetterEventListener>
    ) {
        if (deviceInfoUtil.isOnline) {
            firebaseImpl.getCoffeeMakerEntity(weakListener)
        } else {
            restImpl.getCoffeeMakerEntity(weakListener)
        }
    }

    override fun turnOn(
        weakListener: WeakReference<DataPosterEventListener>,
        turnOn: Boolean
    ) {
        if (deviceInfoUtil.isOnline) {
            firebaseImpl.turnOnCoffeeMakerEntity(weakListener, turnOn)
        } else {
            restImpl.turnOnCoffeeMakerEntity(weakListener, turnOn)
        }
    }
}