package com.eveexite.demoarch.core

import com.eveexite.demoarch.core.data.CoreRestModuleTest
import com.eveexite.demoarch.core.device.CoreDeviceModule
import com.eveexite.demoarch.core.presentation.CoreActivityBuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.DaggerApplication
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 */
@Singleton
@Component(
    modules = [
        CoreModule::class,
        CoreRestModuleTest::class,
        CoreDeviceModule::class,
        CoreActivityBuildersModule::class,
        AndroidInjectionModule::class
    ]
)
interface CoreComponentTest: CoreComponent {

    /**
     * The methods below are sort of a 'promise' that this Component can provide these objects to dependent Components.
     * This is done because we cannot use the sub-components (hard coded connection) with our (dynamic) feature modules.
     */

    val okHttpClient: OkHttpClient

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: DaggerApplication): CoreComponentTest
    }
}