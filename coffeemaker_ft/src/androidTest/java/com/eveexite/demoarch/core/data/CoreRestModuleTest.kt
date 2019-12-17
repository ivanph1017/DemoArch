package com.eveexite.demoarch.core.data

import android.content.Context
import com.eveexite.demoarch.coffeemaker_ft.CoffeeMakerAppTest
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
import javax.net.ssl.SSLSocketFactory

private const val LOCALHOST: String = "localhost"
private const val HTTPS = "https"
private const val TIMEOUT: Long = 20
const val PORT: Int = 9000

@Module
class CoreRestModuleTest: CoreRestModule() {

    private fun createLocalHeldCertificate(): HeldCertificate {
        val localhostAddress: String = InetAddress.getByName(LOCALHOST).canonicalHostName
        return HeldCertificate.Builder()
            .addSubjectAlternativeName(localhostAddress)
            .build()
    }

    private fun createServerSslSocketFactory(localHeldCertificate: HeldCertificate): SSLSocketFactory {
        val serverCertificates: HandshakeCertificates = HandshakeCertificates.Builder()
            .heldCertificate(localHeldCertificate)
            .build()
        return serverCertificates.sslSocketFactory()
    }

    private fun createCertificatePinner(localHeldCertificate: HeldCertificate): CertificatePinner {
        val localhostAddress: String = InetAddress.getByName(LOCALHOST).canonicalHostName
        return CertificatePinner.Builder()
            .add(localhostAddress, CertificatePinner.pin(localHeldCertificate.certificate))
            .build()
    }

    override fun createOkHttpClient(context: Context): OkHttpClient {
        val localHeldCertificate: HeldCertificate = createLocalHeldCertificate()
        val certificatePinner: CertificatePinner = createCertificatePinner(localHeldCertificate)
        val clientCertificates: HandshakeCertificates = HandshakeCertificates.Builder()
            .addTrustedCertificate(localHeldCertificate.certificate)
            .build()

        val serverSslSocketFactory: SSLSocketFactory = createServerSslSocketFactory(localHeldCertificate)
        val testRestServerBuilder = TestRestServer.Builder()
            .serverSSLSocketFactory(serverSslSocketFactory)
        val coffeeMakerAppTest = context as CoffeeMakerAppTest
        coffeeMakerAppTest.mockWebServer = testRestServerBuilder.build().mockWebServer

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

    override fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val localhostAddress: String = InetAddress.getByName(LOCALHOST).canonicalHostName
        val baseUrl = "${HTTPS}://${localhostAddress}:${PORT}"
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}