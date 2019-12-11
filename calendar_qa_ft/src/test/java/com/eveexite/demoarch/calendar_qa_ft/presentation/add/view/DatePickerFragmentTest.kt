package com.eveexite.demoarch.calendar_qa_ft.presentation.add.view

import android.widget.DatePicker
import com.eveexite.demoarch.calendar_qa_ft.presentation.ViewModelFactory
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.END_DATE_EVENT
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.START_DATE_EVENT
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private const val YEAR: Int  = 2019

private const val MONTH: Int  = 12

private const val DAY: Int  = 5

private const val INVALID_DATE_TYPE: Int = -1

@RunWith(MockitoJUnitRunner::class)
class DatePickerFragmentTest {

    @Mock
    private lateinit var viewModelFactory: ViewModelFactory
    @Mock
    private lateinit var datePicker: DatePicker

    private lateinit var datePickerFragment: DatePickerFragment

    @Test
    fun testOnStartDateSet() {
        datePickerFragment = DatePickerFragment(viewModelFactory, START_DATE_EVENT)
        datePickerFragment.onDateSet(datePicker, YEAR, MONTH, DAY)
    }

    @Test
    fun testOnEndDateSet() {
        datePickerFragment = DatePickerFragment(viewModelFactory, END_DATE_EVENT)
        datePickerFragment.onDateSet(datePicker, YEAR, MONTH, DAY)
    }

    @Test
    fun testOnInvalidDateType() {
        datePickerFragment = DatePickerFragment(viewModelFactory, INVALID_DATE_TYPE)
        datePickerFragment.onDateSet(datePicker, YEAR, MONTH, DAY)
    }

}