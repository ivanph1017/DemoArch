package com.eveexite.demoarch.core.presentation

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CoreBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): CoreActivity
}