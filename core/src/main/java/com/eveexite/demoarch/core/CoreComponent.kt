package com.eveexite.demoarch.core

import android.content.Context
import com.eveexite.demoarch.core.data.CoreRestModule
import com.eveexite.demoarch.core.device.CoreDeviceModule
import com.eveexite.demoarch.core.device.DeviceInfoUtil
import com.eveexite.demoarch.core.device.JsonUtil
import com.eveexite.demoarch.core.presentation.CoreActivityBuildersModule
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
        CoreActivityBuildersModule::class,
        AndroidInjectionModule::class
    ]
)
interface CoreComponent: AndroidInjector<DaggerApplication> {

    /**
     * The methods below are sort of a 'promise' that this Component can provide these objects to dependent Components.
     * This is done because we cannot use the sub-components (hard coded connection) with our (dynamic) feature modules.
     */

    val context: Context
    val retrofit: Retrofit
    val deviceInfoUtil: DeviceInfoUtil
    val jsonUtil: JsonUtil

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: DaggerApplication): CoreComponent
    }
}