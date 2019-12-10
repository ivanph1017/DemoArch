package com.eveexite.demoarch.core

import android.app.Application
import android.content.Context
import com.eveexite.demoarch.core.data.CoreRestModule
import com.eveexite.demoarch.core.data.CoreRestServerFactory
import com.eveexite.demoarch.core.device.CoreDeviceModule
import com.eveexite.demoarch.core.device.DeviceInfoUtil
import com.eveexite.demoarch.core.presentation.CoreBuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 */
@Singleton
@Component(
    modules = [
        CoreModule::class,
        CoreRestModule::class,
        CoreDeviceModule::class,
        CoreBuildersModule::class,
        AndroidInjectionModule::class
    ]
)
interface CoreComponent: AndroidInjector<DaggerApplication> {

    /**
     * The methods below are sort of a 'promise' that this Component can provide these objects to dependent Components.
     * This is done because we cannot use the sub-components (hard coded connection) with our (dynamic) feature modules.
     */

    val context: Context
    val port: Int
    val retrofit: Retrofit
    val coreRestServerFactory: CoreRestServerFactory
    val deviceInfoUtil: DeviceInfoUtil

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): CoreComponent
    }
}