package com.eveexite.demoarch.calendar_qa_ft.presentation.add.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.eveexite.demoarch.calendar_qa_ft.R
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.*
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.model.AddModel
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

private const val YEAR:Int = 2019

private const val MONTH: Int = 12

private const val DAY: Int = 5

private const val DATE: String = "05/12/2019"

private const val HOUR_OF_DAY: Int = 15

private const val START_MINUTE: Int = 30

private const val END_MINUTE: Int = 31

private const val START_TIME: String = "03:30 PM"

private const val END_TIME: String = "03:31 PM"

private const val INVALID_TYPE: Int = 100

@RunWith(MockitoJUnitRunner::class)
class AddViewModelTest {

    @Rule
    @JvmField
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var model: AddModel

    @Mock
    private lateinit var dataObserver: Observer<AddModel>

    @Mock
    private lateinit var askPermissionObserver: Observer<AddModel>

    @Mock
    private lateinit var errorMessageResObserver: Observer<Int>

    private lateinit var viewModel: AddViewModel

    @Before
    fun setUp() {
        viewModel = AddViewModel(model)
    }

    @After
    fun clean() {
        viewModel.getLiveData().removeObserver(dataObserver)
        viewModel.getAskPermission().removeObserver(askPermissionObserver)
        viewModel.getErrorMessageRes().removeObserver(errorMessageResObserver)
    }

    @Test
    fun testSetStartDate() {
        BDDMockito.given(model.startDate).willReturn(DATE)
        BDDMockito.given(model.endDate).willReturn(EMPTY_STRING)

        viewModel.getLiveData().observeForever(dataObserver)
        viewModel.setDate(START_DATE_EVENT, YEAR, MONTH, DAY)

        Assert.assertEquals(model.startDate, viewModel.getLiveData().value?.startDate)
        Assert.assertEquals(model.endDate, viewModel.getLiveData().value?.endDate)
    }

    @Test
    fun testSetStartTime() {
        BDDMockito.given(model.startTime).willReturn(START_TIME)
        BDDMockito.given(model.endTime).willReturn(EMPTY_STRING)

        viewModel.getLiveData().observeForever(dataObserver)
        viewModel.setTime(START_TIME_EVENT, HOUR_OF_DAY, START_MINUTE)

        Assert.assertEquals(model.startTime, viewModel.getLiveData().value?.startTime)
        Assert.assertEquals(model.endTime, viewModel.getLiveData().value?.endTime)
    }

    @Test
    fun testSetEndDate() {
        BDDMockito.given(model.startDate).willReturn(EMPTY_STRING)
        BDDMockito.given(model.endDate).willReturn(DATE)

        viewModel.getLiveData().observeForever(dataObserver)
        viewModel.setDate(END_DATE_EVENT, YEAR, MONTH, DAY)

        Assert.assertEquals(model.startDate, viewModel.getLiveData().value?.startDate)
        Assert.assertEquals(model.endDate, viewModel.getLiveData().value?.endDate)
    }

    @Test
    fun testSetEndTime() {
        BDDMockito.given(model.startTime).willReturn(EMPTY_STRING)
        BDDMockito.given(model.endTime).willReturn(END_TIME)

        viewModel.getLiveData().observeForever(dataObserver)
        viewModel.setTime(END_TIME_EVENT, HOUR_OF_DAY, END_MINUTE)

        Assert.assertEquals(model.startTime, viewModel.getLiveData().value?.startTime)
        Assert.assertEquals(model.endTime, viewModel.getLiveData().value?.endTime)
    }

    @Test
    fun testSetInvalidDateType() {
        BDDMockito.given(model.startDate).willReturn(EMPTY_STRING)
        BDDMockito.given(model.endDate).willReturn(EMPTY_STRING)

        viewModel.getLiveData().observeForever(dataObserver)
        viewModel.setDate(INVALID_TYPE, YEAR, MONTH, DAY)

        Assert.assertEquals(model.startDate, viewModel.getLiveData().value?.startDate)
        Assert.assertEquals(model.endDate, viewModel.getLiveData().value?.endDate)
    }

    @Test
    fun testSetInvalidTimeType() {
        BDDMockito.given(model.startTime).willReturn(EMPTY_STRING)
        BDDMockito.given(model.endTime).willReturn(EMPTY_STRING)

        viewModel.getLiveData().observeForever(dataObserver)
        viewModel.setTime(INVALID_TYPE, HOUR_OF_DAY, END_MINUTE)

        Assert.assertEquals(model.startTime, viewModel.getLiveData().value?.startTime)
        Assert.assertEquals(model.endTime, viewModel.getLiveData().value?.endTime)
    }

    @Test
    fun testAddEventWithValidDateTimes() {
        val modelClone = Mockito.mock(AddModel::class.java)
        BDDMockito.given(model.hasValidDateTimes()).willReturn(true)
        BDDMockito.given(model.clone()).willReturn(modelClone)

        viewModel.getAskPermission().observeForever(askPermissionObserver)
        viewModel.addEvent()

        Assert.assertNotNull(viewModel.getAskPermission().value)
    }

    @Test
    fun testAddEventWithInValidDateTimes() {
        BDDMockito.given(model.hasValidDateTimes()).willReturn(false)

        viewModel.getErrorMessageRes().observeForever(errorMessageResObserver)
        viewModel.addEvent()

        Assert.assertEquals(R.string.invalid_dates_error_message.toLong(), viewModel.getErrorMessageRes().value?.toLong())
        Assert.assertNull(viewModel.getAskPermission().value)
    }
}