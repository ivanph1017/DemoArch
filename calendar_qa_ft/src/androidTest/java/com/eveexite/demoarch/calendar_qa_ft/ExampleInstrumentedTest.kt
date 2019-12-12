package com.eveexite.demoarch.calendar_qa_ft

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.view.AddActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<AddActivity> = ActivityTestRule(AddActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.eveexite.demoarch.calendar_qa_ft.test", appContext.packageName)
    }
}
