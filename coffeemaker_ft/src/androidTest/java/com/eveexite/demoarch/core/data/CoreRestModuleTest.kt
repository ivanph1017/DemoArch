package com.eveexite.demoarch.core.data

import dagger.Module
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

private const val LOCALHOST: String = "localhost"
private const val HTTPS = "https"
private const val TIMEOUT: Long = 20

@Module
class CoreRestModuleTest: CoreRestModule() {

    override fun createOkHttpClient(
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

    override fun getPort(): Int = 9000

    override fun createRetrofit(
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
}