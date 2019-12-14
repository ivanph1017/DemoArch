package com.eveexite.demoarch.core.presentation

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CoreActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCoreActivityInjector(): CoreActivity
}