package com.eveexite.demoarch

import android.content.Context
import com.eveexite.demoarch.core.CoreComponent
import com.eveexite.demoarch.core.CoreComponentProvider
import com.eveexite.demoarch.core.DaggerCoreComponent
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

open class App: DaggerApplication(), CoreComponentProvider {

    private lateinit var coreComponent: CoreComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        coreComponent = DaggerCoreComponent.factory().create(this)
        return coreComponent
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    override fun provideCoreComponent(): CoreComponent = coreComponent

}