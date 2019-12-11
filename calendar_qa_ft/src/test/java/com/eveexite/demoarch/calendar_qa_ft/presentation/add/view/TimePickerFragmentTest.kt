package com.eveexite.demoarch.calendar_qa_ft.presentation.add.view

import android.widget.TimePicker
import com.eveexite.demoarch.calendar_qa_ft.presentation.ViewModelFactory
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.END_TIME_EVENT
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.START_TIME_EVENT
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private const val HOUR_OF_DAY: Int = 15

private const val MINUTE: Int = 30

private const val INVALID_TIME_TYPE: Int = -1

@RunWith(MockitoJUnitRunner::class)
class TimePickerFragmentTest {

    @Mock
    private lateinit var viewModelFactory: ViewModelFactory
    @Mock
    private lateinit var timePicker: TimePicker

    private lateinit var timePickerFragment: TimePickerFragment

    @Test
    fun testOnStartTimeSet() {
        timePickerFragment = TimePickerFragment(viewModelFactory, START_TIME_EVENT)
        timePickerFragment.onTimeSet(timePicker, HOUR_OF_DAY, MINUTE)
    }

    @Test
    fun testOnEndTimeSet() {
        timePickerFragment = TimePickerFragment(viewModelFactory, END_TIME_EVENT)
        timePickerFragment.onTimeSet(timePicker, HOUR_OF_DAY, MINUTE)
    }

    @Test
    fun testOnInvalidTimeType() {
        timePickerFragment = TimePickerFragment(viewModelFactory, INVALID_TIME_TYPE)
        timePickerFragment.onTimeSet(timePicker, HOUR_OF_DAY, MINUTE)
    }
}