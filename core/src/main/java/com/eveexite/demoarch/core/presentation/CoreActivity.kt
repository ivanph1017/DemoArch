package com.eveexite.demoarch.core.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.eveexite.demoarch.core.R
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class CoreActivity : AppCompatActivity() {

    private lateinit var appPackageName: String
    private lateinit var moduleCoffeeMakerFt: String
    private lateinit var moduleCalendarFt: String
    private lateinit var coffeeMakerClassName: String
    private lateinit var calendarClassName: String
    private lateinit var manager: SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core)
        init()
        setupClickListeners()
    }

    private fun init() {
        appPackageName = applicationContext.packageName
        moduleCoffeeMakerFt = getString(R.string.module_coffeemaker_ft)
        moduleCalendarFt = getString(R.string.module_calendar_ft)
        coffeeMakerClassName = "${appPackageName}.${moduleCoffeeMakerFt}.presentation.coffeemaker.view.CoffeeMakerActivity"
        calendarClassName = "${appPackageName}.${moduleCalendarFt}.CalendarActivity"
        manager = SplitInstallManagerFactory.create(this)
    }

    private fun setupClickListeners() {
        findViewById<Button>(R.id.btn_coffee_maker).setOnClickListener {
            loadAndLaunchModule(moduleCoffeeMakerFt)
        }
        findViewById<Button>(R.id.btn_calendar).setOnClickListener {
            loadAndLaunchModule(moduleCalendarFt)
        }
    }

    private fun loadAndLaunchModule(name: String) {

        if (manager.installedModules.contains(name)) {
            onSuccessfulLoad(name, launch = true)
            return
        }

        val request = SplitInstallRequest.newBuilder()
            .addModule(name)
            .build()

        manager.startInstall(request)
            .addOnSuccessListener { sessionId ->
                Log.d(CoreActivity::class.simpleName, sessionId.toString())
            }
            .addOnFailureListener { exception ->
                Log.e(CoreActivity::class.simpleName, exception.toString())
            }
    }

    /**
     * Define what to do once a feature module is loaded successfully.
     * @param moduleName The name of the successfully loaded module.
     * @param launch `true` if the feature module should be launched, else `false`.
     */
    private fun onSuccessfulLoad(moduleName: String, launch: Boolean) {
        if (launch) {
            when (moduleName) {
                moduleCoffeeMakerFt -> launchActivity(coffeeMakerClassName)
                moduleCalendarFt -> launchActivity(calendarClassName)
            }
        }
    }

    /** Launch an activity by its class name. */
    private fun launchActivity(className: String) {
        val intent = Intent().setClassName(packageName, className)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
