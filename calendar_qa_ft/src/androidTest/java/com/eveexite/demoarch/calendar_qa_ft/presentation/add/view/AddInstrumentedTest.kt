package com.eveexite.demoarch.calendar_qa_ft.presentation.add.view

import android.content.Context
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.eveexite.demoarch.calendar_qa_ft.R
import com.google.android.material.textfield.TextInputLayout
import org.apache.commons.lang3.RandomStringUtils
import org.hamcrest.core.AllOf
import org.hamcrest.core.IsNot
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddInstrumentedTest {

    private val anyString: String = "fdsfdsf"
    private val emptyString: String = ""
    private val startDate: String = "15/10/2019"
    private val startTime: String = "03:00 PM"
    private val endDate: String = "16/10/2019"
    private val endTime: String = "04:00 PM"

    @Rule
    @JvmField
    var activityScenarioRule: ActivityScenarioRule<AddActivity> = ActivityScenarioRule(AddActivity::class.java)

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

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
        testFilledField(R.id.layout_title, R.id.et_title, anyString, false)
        testFilledField(R.id.layout_location, R.id.et_location, anyString, false)
        testFilledField(R.id.layout_description, R.id.et_description, anyString, false)
        testFilledField(R.id.layout_start_date, R.id.et_start_date, startDate, true)
        testFilledField(R.id.layout_start_time, R.id.et_start_time, startTime, true)
        testFilledField(R.id.layout_end_date, R.id.et_end_date, endDate, true)
        testFilledField(R.id.layout_end_time, R.id.et_end_time, endTime, true)
        onView(withId(R.id.btn_add)).check(
            matches(
                AllOf.allOf(
                    isDisplayed(), isEnabled())))
    }

    @Test
    fun testDisablingAddButton() {
        testSetUp()
        val layoutResourceIds = intArrayOf(R.id.layout_title, R.id.layout_location, R.id.layout_description,
            R.id.layout_start_date, R.id.layout_start_time, R.id.layout_end_date, R.id.layout_end_time)
        val editTextResourceIds = intArrayOf(R.id.et_title, R.id.et_location, R.id.et_description,
            R.id.et_start_date, R.id.et_start_time, R.id.et_end_date, R.id.et_end_time)
        val texts = arrayOf(anyString, anyString, anyString, startDate, startTime, endDate, endTime)
        val flags = booleanArrayOf(false, false, false, true, true, true, true)
        val length = layoutResourceIds.size
        for (i in 0 until length) {
            testFilledField(layoutResourceIds[i], editTextResourceIds[i], texts[i], flags[i])
        }
        for (i in 0 until length) {
            onView(withId(editTextResourceIds[i])).perform(replaceText(emptyString))
            onView(withId(R.id.btn_add)).check(
                matches(
                    AllOf.allOf(
                        isDisplayed(), IsNot.not(isEnabled()))))
            testFilledField(layoutResourceIds[i], editTextResourceIds[i], texts[i], flags[i])
        }
    }

    private fun testSetUp() {
        testIsDisplayed(R.id.layout_title)
        testIsDisplayed(R.id.et_title)
        testIsDisplayed(R.id.layout_location)
        testIsDisplayed(R.id.et_location)
        testIsDisplayed(R.id.layout_description)
        testIsDisplayed(R.id.et_description)
        testIsDisplayed(R.id.layout_start_date)
        testIsDisplayed(R.id.et_start_date)
        testIsDisplayed(R.id.layout_start_time)
        testIsDisplayed(R.id.et_start_time)
        testIsDisplayed(R.id.layout_end_date)
        testIsDisplayed(R.id.et_end_date)
        testIsDisplayed(R.id.layout_end_time)
        testIsDisplayed(R.id.et_end_time)

        onView(withId(R.id.btn_add)).check(
            matches(
                AllOf.allOf(
                    isDisplayed(), IsNot.not(isEnabled()))))
    }

    private fun testIsDisplayed(@IdRes resourceId: Int) {
        onView(withId(resourceId)).check(
            matches(
                isDisplayed()))
    }

    private fun testErrorMessageOnEmptyField(@IdRes layoutResourceId: Int,
                                             @IdRes editTextResourceId: Int) {
        onView(withId(editTextResourceId))
            .perform(click())
            .perform(replaceText(anyString))
            .perform(replaceText(emptyString))
        assertOnTextInputLayoutError(layoutResourceId, R.string.field_empty_error_message)
    }

    private fun testErrorMessageOnMaxTextExceeded(@IdRes layoutResourceId: Int,
                                                  @IdRes editTextResourceId: Int) {
        var counterMaxLength = 0
        onView(withId(layoutResourceId)).check { view, _ ->
            val textInputLayout = view as TextInputLayout
            counterMaxLength = textInputLayout.counterMaxLength
        }
        val textOverMaxLength = RandomStringUtils.random(counterMaxLength + 1, true, true)
        onView(withId(editTextResourceId))
            .perform(click())
            .perform(replaceText(textOverMaxLength))
        assertOnTextInputLayoutError(layoutResourceId, R.string.field_max_characters_error_message)
    }

    private fun assertOnTextInputLayoutError(@IdRes layoutResourceId: Int,
                                             @StringRes stringResourceId: Int) {
        onView(withId(layoutResourceId))
            .check { view, _ ->
                val textInputLayout = view as TextInputLayout
                Assert.assertTrue(textInputLayout.isErrorEnabled)
                Assert.assertEquals(appContext.resources.getString(stringResourceId),
                    textInputLayout.error.toString())
            }
    }

    private fun testFilledField(@IdRes layoutResourceId: Int,
                                @IdRes editTextResourceId: Int,
                                text: String, dateTimeField: Boolean) {
        if (dateTimeField) {
            onView(withId(editTextResourceId)).perform(replaceText(text))
        } else {
            onView(withId(editTextResourceId))
                .perform(click())
                .perform(replaceText(text))
        }
        assertOnTextInputLayoutFilled(layoutResourceId)
    }

    private fun assertOnTextInputLayoutFilled(@IdRes layoutResourceId: Int) {
        onView(withId(layoutResourceId))
            .check { view, _ ->
                val textInputLayout = view as TextInputLayout
                Assert.assertFalse(textInputLayout.isErrorEnabled)
                Assert.assertTrue(textInputLayout.isEndIconVisible)
            }

    }
}