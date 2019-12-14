package com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.impl

import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.CoffeeMakerRestService
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.datasource.CoffeeMakerDataSource
import com.eveexite.demoarch.coffeemaker_ft.data.coffeemaker.entity.CoffeeMakerEntity
import com.eveexite.demoarch.coffeemaker_ft.data.rest.exception.RestOperationNotSupportedException
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataGetterEventListener
import com.eveexite.demoarch.coffeemaker_ft.data.listener.DataPosterEventListener
import com.eveexite.demoarch.core.data.RestServer
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.ref.WeakReference

private const val HTTP_200: Int = 200

class CoffeeMakerDataSourceRestImpl(
    private val coffeeMakerRestService: CoffeeMakerRestService,
    private val restServerBuilder: RestServer.Builder,
    private val dispatcher: Dispatcher,
    private val port: Int
):
    CoffeeMakerDataSource {

    override fun getSingleCoffeeMakerEntity(weakListener: WeakReference<DataGetterEventListener>) {
        val server: MockWebServer = restServerBuilder
            .dispatcher(dispatcher)
            .build()
            .mockWebServer
        server.start(port)
        val call: Call<CoffeeMakerEntity> = coffeeMakerRestService.getCoffeeMakerEntity()
        call.enqueue(
            getCallback(weakListener, server)
        )
    }

    override fun getCoffeeMakerEntity(weakListener: WeakReference<DataGetterEventListener>) {
        val server: MockWebServer = restServerBuilder
            .dispatcher(dispatcher)
            .build()
            .mockWebServer
        server.start(port)
        val call: Call<CoffeeMakerEntity> = coffeeMakerRestService.getCoffeeMakerEntity()
        call.enqueue(
            getCallback(weakListener, server)
        )
    }

    private fun getCallback(
        weakListener: WeakReference<DataGetterEventListener>,
        server: MockWebServer
    ) = object : Callback<CoffeeMakerEntity> {
            override fun onResponse(
                call: Call<CoffeeMakerEntity>,
                response: Response<CoffeeMakerEntity>
            ) {
                checkGetResponse(weakListener, response)
                server.shutdown()
            }

            override fun onFailure(
                call: Call<CoffeeMakerEntity>,
                throwable: Throwable
            ) {
                weakListener.get()?.onError(
                    Exception(throwable)
                )
                server.shutdown()
            }
        }

    fun checkGetResponse(
        weakListener: WeakReference<DataGetterEventListener>,
        response: Response<CoffeeMakerEntity>
    ) {
        when {
            response.isSuccessful && response.code() == HTTP_200 -> response.body()?.let {
                weakListener.get()?.onDataChange(it)
            }
            response.isSuccessful -> weakListener.get()?.onError(
                IOException("response was not HTTP 200")
            )
            else -> weakListener.get()?.onError(
                HttpException(response)
            )
        }
    }

    override fun turnOnCoffeeMakerEntity(
        weakListener: WeakReference<DataPosterEventListener>,
        turnOn: Boolean
    ) {
        weakListener.get()?.onError(
            RestOperationNotSupportedException(
                "rest data source unable to turnOn CoffeeMakerEntity"
            )
        )
    }
}