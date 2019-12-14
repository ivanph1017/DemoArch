package com.eveexite.demoarch.core.data

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import javax.net.ssl.SSLSocketFactory

class RestServer private constructor(
  val mockWebServer: MockWebServer
) {

    data class Builder(
        private val mockWebServer: MockWebServer = MockWebServer()
    ) {
        fun serverSSLSocketFactory(serverSSLSocketFactory: SSLSocketFactory) = apply {
            mockWebServer.useHttps(serverSSLSocketFactory, false)
        }

        fun dispatcher(dispatcher: Dispatcher) = apply {
            mockWebServer.dispatcher = dispatcher
        }

        fun build() = RestServer(mockWebServer)
    }
}