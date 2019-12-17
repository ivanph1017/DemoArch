package com.eveexite.demoarch.core.data

import android.util.Log
import androidx.annotation.RawRes
import com.eveexite.demoarch.coffeemaker_ft.BuildConfig
import com.eveexite.demoarch.coffeemaker_ft.R
import com.eveexite.demoarch.core.device.JsonUtil
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class TestDispatcher(private val jsonUtil: JsonUtil): Dispatcher() {

    @RawRes private val rawFile: Array<Int> = arrayOf(
        R.raw.coffee_maker_unplugged,
        R.raw.coffee_maker_ready,
        R.raw.not_enough_water,
        R.raw.coffee_ready,
        R.raw.coffee_maker_ready_preparing_coffee,
        R.raw.coffee_maker_not_ready_preparing_coffee,
        R.raw.coffee_maker_resting_5,
        R.raw.coffee_maker_resting_4,
        R.raw.coffee_maker_resting_3,
        R.raw.coffee_maker_resting_2,
        R.raw.coffee_maker_resting_1
    )
    private var index: Int = 0

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path: String? = request.path
        return if (path != null) pickMockResponse(path) else MockResponse().setResponseCode(500)
    }

    private fun pickMockResponse(path: String): MockResponse {
        if (path.contains(BuildConfig.COFFEE_MAKER)) {
            val jsonBody = jsonUtil.loadJson(rawFile[index++])
            if (index == rawFile.size) {
                index = 0
            }
            Log.d("lol", index.toString())
            if (jsonBody != null) {
                return MockResponse().setResponseCode(200).setBody(jsonBody)
            }
            return MockResponse().setResponseCode(404)
        }
        return MockResponse().setResponseCode(500)
    }
}