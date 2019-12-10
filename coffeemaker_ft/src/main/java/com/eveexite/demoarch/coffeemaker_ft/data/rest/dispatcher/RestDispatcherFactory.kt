package com.eveexite.demoarch.coffeemaker_ft.data.rest.dispatcher

import com.eveexite.demoarch.coffeemaker_ft.data.util.JsonUtil

class RestDispatcherFactory(private val jsonUtil: JsonUtil) {

    fun createDispatcher() = RestDispatcher(jsonUtil)
}