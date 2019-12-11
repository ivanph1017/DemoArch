package com.eveexite.demoarch.calendar_qa_ft.presentation.add

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.eveexite.demoarch.calendar_qa_ft.R
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.view.AddActivity
import com.google.android.material.textfield.TextInputLayout
import org.apache.commons.lang3.RandomStringUtils
import org.hamcrest.core.AllOf
import org.hamcrest.core.IsNot
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val ANY_STRING: String = "fdsfdsf"
private const val EMPTY_STRING: String = ""
private const val START_DATE: String = "15/10/2019"
private const val START_TIME: String = "03:00 PM"
private const val END_DATE: String = "16/10/2019"
private const val END_TIME: String = "04:00 PM"

@RunWith(AndroidJUnit4::class)
class AddActivityTest {

    @Rule
    var activityTestRule: ActivityTestRule<AddActivity> = ActivityTestRule(AddActivity::class.java)

    @Test
    fun testErrorMessageOnEmptyFields() {
        testSetUp()
        testErrorMessageOnEmptyField(R.id.layout_title, R.id.et_title)
        testErrorMessageOnEmptyField(R.id.layout_location, R.id.et_location)
        testErrorMessageOnEmptyField(R.id.layout_description, R.id.et_description)
    }

    @Test
    fun testErrorMessageOnMaxTextExceededFields() {
        testSetUp()
        testErrorMessageOnMaxTextExceeded(R.id.layout_title, R.id.et_title)
        testErrorMessageOnMaxTextExceeded(R.id.layout_location, R.id.et_location)
        testErrorMessageOnMaxTextExceeded(R.id.layout_description, R.id.et_description)
    }

    @Test
    fun testFilledFields() {
        testSetUp()
        testFilledField(R.id.layout_title, R.id.et_title, ANY_STRING, false)
        testFilledField(R.id.layout_location, R.id.et_location, ANY_STRING, false)
        testFilledField(R.id.layout_description, R.id.et_description, ANY_STRING, false)
        testFilledField(R.id.layout_start_date, R.id.et_start_date, START_DATE, true)
        testFilledField(R.id.layout_start_time, R.id.et_start_time, START_TIME, true)
        testFilledField(R.id.layout_end_date, R.id.et_end_date, END_DATE, true)
        testFilledField(R.id.layout_end_time, R.id.et_end_time, END_TIME, true)
        Espresso.onView(ViewMatchers.withId(R.id.btn_add)).check(
            ViewAssertions.matches(
                AllOf.allOf(
                    ViewMatchers.isDisplayed(), ViewMatchers.isEnabled())))
    }

    @Test
    fun testDisablingAddButton() {
        testSetUp()
        val layoutResourceIds = intArrayOf(R.id.layout_title, R.id.layout_location, R.id.layout_description,
            R.id.layout_start_date, R.id.layout_start_time, R.id.layout_end_date, R.id.layout_end_time)
        val editTextResourceIds = intArrayOf(R.id.et_title, R.id.et_location, R.id.et_description,
            R.id.et_start_date, R.id.et_start_time, R.id.et_end_date, R.id.et_end_time)
        val texts = arrayOf(ANY_STRING, ANY_STRING, ANY_STRING, START_DATE, START_TIME, END_DATE, END_TIME)
        val flags = booleanArrayOf(false, false, false, true, true, true, true)
        val length = layoutResourceIds.size
        for (i in 0 until length) {
            testFilledField(layoutResourceIds[i], editTextResourceIds[i], texts[i], flags[i])
        }
        for (i in 0 until length) {
            Espresso.onView(ViewMatchers.withId(editTextResourceIds[i])).perform(ViewActions.replaceText(EMPTY_STRING))
            Espresso.onView(ViewMatchers.withId(R.id.btn_add)).check(
                ViewAssertions.matches(
                    AllOf.allOf(
                        ViewMatchers.isDisplayed(), IsNot.not(ViewMatchers.isEnabled()))))
            testFilledField(layoutResourceIds[i], editTextResourceIds[i], texts[i], flags[i])
        }
    }

    private fun testSetUp() {
        Espresso.onView(ViewMatchers.withId(R.id.layout_title)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.et_title)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.layout_location)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.et_location)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.layout_description)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.et_description)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.layout_start_date)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.et_start_date)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.layout_start_time)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.et_start_time)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.layout_end_date)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.et_end_date)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.layout_end_time)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.et_end_time)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btn_add)).check(
            ViewAssertions.matches(
                AllOf.allOf(
                    ViewMatchers.isDisplayed(), IsNot.not(ViewMatchers.isEnabled()))))
    }

    private fun testErrorMessageOnEmptyField(@IdRes layoutResourceId: Int,
                                             @IdRes editTextResourceId: Int) {
        Espresso.onView(ViewMatchers.withId(editTextResourceId)).perform(ViewActions.click())
            .perform(ViewActions.replaceText(ANY_STRING))
            .perform(ViewActions.replaceText(EMPTY_STRING))
        val textInputLayout = activityTestRule.activity.findViewById<TextInputLayout>(layoutResourceId)

        Assert.assertTrue(textInputLayout.isErrorEnabled)
        Assert.assertTrue(textInputLayout.isEndIconVisible)
        Assert.assertEquals(activityTestRule.activity.getString(R.string.field_empty_error_message),
            textInputLayout.error.toString())
    }

    private fun testErrorMessageOnMaxTextExceeded(@IdRes layoutResourceId: Int,
                                                  @IdRes editTextResourceId: Int) {
        val textInputLayout = activityTestRule.activity.findViewById<TextInputLayout>(layoutResourceId)
        val textOverMaxLength = RandomStringUtils.random(textInputLayout.counterMaxLength + 1, true, true)
        Espresso.onView(ViewMatchers.withId(editTextResourceId)).perform(ViewActions.click())

            .perform(ViewActions.replaceText(textOverMaxLength))
        Assert.assertTrue(textInputLayout.isErrorEnabled)
        Assert.assertTrue(textInputLayout.isEndIconVisible)
        Assert.assertEquals(activityTestRule.activity.getString(R.string.field_max_characters_error_message),
            textInputLayout.error.toString())
    }

    private fun testFilledField(@IdRes layoutResourceId: Int,
                                @IdRes editTextResourceId: Int,
                                text: String, dateTimeField: Boolean) {
        val textInputLayout = activityTestRule.activity.findViewById<TextInputLayout>(layoutResourceId)
        if (dateTimeField) {
            Espresso.onView(ViewMatchers.withId(editTextResourceId)).perform(ViewActions.replaceText(text))
        } else {
            Espresso.onView(ViewMatchers.withId(editTextResourceId)).perform(ViewActions.click())
                .perform(ViewActions.replaceText(text))
        }
        Assert.assertFalse(textInputLayout.isErrorEnabled)
        Assert.assertTrue(textInputLayout.isEndIconVisible)
    }
}