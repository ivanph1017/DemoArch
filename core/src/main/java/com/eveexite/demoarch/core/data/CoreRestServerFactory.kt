package com.eveexite.demoarch.core.data

import okhttp3.mockwebserver.MockWebServer
import javax.net.ssl.SSLSocketFactory

class CoreRestServerFactory(private val serverSSLSocketFactory: SSLSocketFactory) {

    fun createServer(): MockWebServer {
        val mockWebServer = MockWebServer()
        mockWebServer.useHttps(serverSSLSocketFactory, false)
        return mockWebServer
    }
}