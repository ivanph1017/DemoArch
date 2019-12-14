package com.eveexite.demoarch.core

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

open class CoreApp: DaggerApplication(), CoreComponentProvider {

    protected open lateinit var coreComponent: CoreComponent
    protected lateinit var map: MutableMap<String, BaseFeatureComponent?>
        private set

    override fun onCreate() {
        super.onCreate()
        map = mutableMapOf()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        coreComponent = DaggerCoreComponent.factory().create(this)
        return coreComponent
    }

    override fun provideCoreComponent(): CoreComponent = coreComponent

    open fun putBaseFeatureComponent(className: String, featureComponent: BaseFeatureComponent) {
        map[className] = featureComponent
    }

    fun getBaseFeatureComponent(className: String): BaseFeatureComponent? = map[className]

}