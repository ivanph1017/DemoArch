package com.eveexite.demoarch.coffeemaker_ft

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class CoffeeMakerRunner: AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader,
        className: String,
        context: Context
    ): Application {
        return super.newApplication(classLoader, CoffeeMakerAppTest::class.qualifiedName, context)
    }
}