package com.eveexite.demoarch.coffeemaker_ft

import androidx.test.espresso.IdlingResource
import com.eveexite.demoarch.App
import com.eveexite.demoarch.core.device.JsonUtil
import com.eveexite.demoarch.core.BaseFeatureComponent
import com.eveexite.demoarch.core.CoreComponentTest
import com.eveexite.demoarch.core.DaggerCoreComponentTest
import com.eveexite.demoarch.core.data.PORT
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import okhttp3.mockwebserver.MockWebServer

class CoffeeMakerAppTest: App() {

    lateinit var resource: IdlingResource
        private set
    lateinit var mockWebServer: MockWebServer
    lateinit var jsonUtil: JsonUtil
        private set

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        coreComponent = DaggerCoreComponentTest.factory().create(this)
        initProperties()
        return coreComponent
    }

    private fun initProperties() {
        val thread = Thread(createInitBlock())
        thread.start()
        thread.join()
    }

    private fun createInitBlock() = Runnable {
        val coreComponentTest = coreComponent as CoreComponentTest
        resource = OkHttp3IdlingResource.create("OkHttp", coreComponentTest.okHttpClient)
        jsonUtil = coreComponentTest.jsonUtil
        mockWebServer.start(PORT)
    }

    override fun putBaseFeatureComponent(
        className: String,
        featureComponent: BaseFeatureComponent
    ) {
        val featureComponentTest: FeatureComponentTest = DaggerFeatureComponentTest.factory()
            .create(coreComponent)
        super.putBaseFeatureComponent(className, featureComponentTest)
    }

    override fun onTerminate() {
        super.onTerminate()
        val thread = Thread(
            Runnable { mockWebServer.shutdown() }
        )
        thread.start()
        thread.join()
    }
}