package com.eveexite.demoarch.core.device

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Module
class CoreDeviceModule {

    @Singleton
    @Provides
    fun provideDeviceInfoUtil(application: DaggerApplication) = DeviceInfoUtil(application)

    @Singleton
    @Provides
    fun provideObjectMapper() = ObjectMapper()

    @Singleton
    @Provides
    fun provideJsonUtil(
        context: Context,
        objectMapper: ObjectMapper
    ) =
        JsonUtil(
            context,
            objectMapper
        )
}