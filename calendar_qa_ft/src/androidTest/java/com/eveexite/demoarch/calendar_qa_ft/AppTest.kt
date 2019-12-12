package com.eveexite.demoarch.calendar_qa_ft

import com.eveexite.demoarch.core.CoreComponent
import com.eveexite.demoarch.core.CoreComponentProvider
import com.eveexite.demoarch.core.DaggerCoreComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class AppTest: DaggerApplication(), CoreComponentProvider {

    private lateinit var coreComponent: CoreComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        coreComponent = DaggerCoreComponent.factory().create(this)
        return coreComponent
    }

    override fun provideCoreComponent(): CoreComponent = coreComponent
}