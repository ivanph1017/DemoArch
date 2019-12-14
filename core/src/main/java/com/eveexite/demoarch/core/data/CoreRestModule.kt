package com.eveexite.demoarch.core.data

import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.HeldCertificate
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.InetAddress
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLSocketFactory

private const val LOCALHOST: String = "localhost"
private const val HTTPS = "https"
private const val TIMEOUT: Long = 20

@Module
open class CoreRestModule {

    @Singleton
    @Provides
    @Named("localHost")
    fun provideHeldCertificate(): HeldCertificate {
        val localhostAddress: String = InetAddress.getByName(LOCALHOST).canonicalHostName
        return HeldCertificate.Builder()
            .addSubjectAlternativeName(localhostAddress)
            .build()
    }

    @Singleton
    @Provides
    @Named("server")
    fun provideSslSocketFactory(
        @Named("localHost") heldCertificate: HeldCertificate
    ): SSLSocketFactory {
        val serverCertificates: HandshakeCertificates = HandshakeCertificates.Builder()
            .heldCertificate(heldCertificate)
            .build()
        return serverCertificates.sslSocketFactory()
    }

    @Singleton
    @Provides
    fun provideCertificatePinner(
        @Named("localHost") heldCertificate: HeldCertificate
    ): CertificatePinner {
        val localhostAddress: String = InetAddress.getByName(LOCALHOST).canonicalHostName
        return CertificatePinner.Builder()
            .add(localhostAddress, CertificatePinner.pin(heldCertificate.certificate))
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named("localHost") heldCertificate: HeldCertificate,
        certificatePinner: CertificatePinner
    ): OkHttpClient = createOkHttpClient(heldCertificate, certificatePinner)

    protected open fun createOkHttpClient(
        localHeldCertificate: HeldCertificate,
        certificatePinner: CertificatePinner
    ): OkHttpClient {
        val clientCertificates: HandshakeCertificates = HandshakeCertificates.Builder()
            .addTrustedCertificate(localHeldCertificate.certificate)
            .build()

        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectionSpecs(arrayListOf(ConnectionSpec.MODERN_TLS))
            .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
            .certificatePinner(certificatePinner)
            .sslSocketFactory(
                clientCertificates.sslSocketFactory(),
                clientCertificates.trustManager
            )
            .build()

    }

    protected open fun getPort(): Int = 8080

    @Singleton
    @Provides
    fun providePort(): Int = getPort()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, port: Int): Retrofit = createRetrofit(okHttpClient, port)

    protected open fun createRetrofit(
        okHttpClient: OkHttpClient,
        port: Int
    ): Retrofit {
        val localhostAddress: String = InetAddress.getByName(LOCALHOST).canonicalHostName
        val baseUrl = "${HTTPS}://${localhostAddress}:${port}"
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRestServerBuilder(
        @Named("server") sslSocketFactory: SSLSocketFactory
    ) = RestServer.Builder().serverSSLSocketFactory(sslSocketFactory)
}