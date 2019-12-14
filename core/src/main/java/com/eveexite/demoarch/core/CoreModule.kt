package com.eveexite.demoarch.core

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Module
class CoreModule {

    @Singleton
    @Provides
    fun provideContext(application: DaggerApplication): Context = application
}