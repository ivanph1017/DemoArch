package com.eveexite.demoarch.calendar_qa_ft.presentation.configure

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eveexite.demoarch.calendar_qa_ft.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfigureActivityTest {

    @Rule
    @JvmField
    var activityScenarioRule: ActivityScenarioRule<ConfigureActivity> = ActivityScenarioRule(ConfigureActivity::class.java)

    private lateinit var scenario: ActivityScenario<ConfigureActivity>

    @Before
    fun setUp() {
        scenario = activityScenarioRule.scenario
    }

    @After
    fun tearDown() {
        scenario.close()
    }

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