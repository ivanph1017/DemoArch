package com.eveexite.demoarch.core.device

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreDeviceModule {

    @Singleton
    @Provides
    fun provideDeviceInfoUtil(application: Application) = DeviceInfoUtil(application)
}