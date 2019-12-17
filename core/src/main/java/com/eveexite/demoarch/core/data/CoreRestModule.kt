package com.eveexite.demoarch.core.data

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.eveexite.demoarch.core.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT: Long = 20

@Module
open class CoreRestModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient = createOkHttpClient(context)

    protected open fun createOkHttpClient(context: Context): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .connectionSpecs(arrayListOf(ConnectionSpec.MODERN_TLS))
        .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
        .addInterceptor(ChuckerInterceptor(context))
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = createRetrofit(okHttpClient)

    protected open fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}