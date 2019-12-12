package com.eveexite.demoarch.calendar_qa_ft.presentation.finished

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
class FinishedActivityTest {

    @Rule
    @JvmField
    var activityScenarioRule: ActivityScenarioRule<FinishedActivity> = ActivityScenarioRule(FinishedActivity::class.java)

    private lateinit var scenario: ActivityScenario<FinishedActivity>

    @Before
    fun setUp() {
        scenario = activityScenarioRule.scenario
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testBackToHomeButtonVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_back_to_home)).check(ViewAssertions.matches(
            ViewMatchers.isDisplayed()))
    }

    @Test
    fun testBackToHomeButtonEnable() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_back_to_home)).check(ViewAssertions.matches(
            ViewMatchers.isEnabled()))
    }

    @Test
    fun testBackToHomeButtonClickable() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_back_to_home)).check(ViewAssertions.matches(
            ViewMatchers.isClickable()))
    }
}