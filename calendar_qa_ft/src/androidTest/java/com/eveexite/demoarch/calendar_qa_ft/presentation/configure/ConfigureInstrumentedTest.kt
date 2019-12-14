package com.eveexite.demoarch.calendar_qa_ft.presentation.configure

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eveexite.demoarch.calendar_qa_ft.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfigureInstrumentedTest {

    @Rule
    @JvmField
    var activityScenarioRule: ActivityScenarioRule<ConfigureActivity> = ActivityScenarioRule(ConfigureActivity::class.java)

    @Test
    fun testConfigureButtonVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_configure)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
    }

    @Test
    fun testConfigureButtonEnable() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_configure)).check(
            ViewAssertions.matches(
                ViewMatchers.isEnabled()))
    }

    @Test
    fun testConfigureButtonClickable() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_configure)).check(
            ViewAssertions.matches(
                ViewMatchers.isClickable()))
    }
}