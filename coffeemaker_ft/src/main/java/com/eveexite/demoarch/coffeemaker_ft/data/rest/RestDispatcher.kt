package com.eveexite.demoarch.coffeemaker_ft.data.rest

import com.eveexite.demoarch.coffeemaker_ft.BuildConfig
import com.eveexite.demoarch.coffeemaker_ft.R
import com.eveexite.demoarch.core.device.JsonUtil
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class RestDispatcher(private val jsonUtil: JsonUtil): Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path: String? = request.path
        return if (path != null) pickMockResponse(path) else MockResponse().setResponseCode(500)
    }

    private fun pickMockResponse(path: String): MockResponse {
        if (path.contains(BuildConfig.COFFEE_MAKER)) {
            val jsonBody = jsonUtil.loadJson(R.raw.coffeemaker_rest)
            if (jsonBody != null) {
                return MockResponse().setResponseCode(200).setBody(jsonBody)
            }
            return MockResponse().setResponseCode(404)
        }
        return MockResponse().setResponseCode(500)
    }
}