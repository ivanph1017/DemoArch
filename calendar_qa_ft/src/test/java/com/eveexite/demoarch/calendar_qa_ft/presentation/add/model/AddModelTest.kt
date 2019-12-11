package com.eveexite.demoarch.calendar_qa_ft.presentation.add.model

import com.eveexite.demoarch.calendar_qa_ft.presentation.add.*
import com.eveexite.demoarch.calendar_qa_ft.presentation.util.DateUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.text.ParseException

private const val YEAR: Int = 2019

private const val MONTH: Int = 12

private const val DAY: Int = 5

private const val DATE: String = "05/01/2020"

private const val HOUR_OF_DAY: Int = 15

private const val START_MINUTE: Int = 30

private const val END_MINUTE: Int = 31

private const val START_TIME: String = "03:30 PM"

private const val END_TIME: String = "03:31 PM"

private const val INVALID_END_MINUTE: Int = 29

private const val INVALID_TYPE: Int = -1

private const val TEXT: String = "ABCEDFGDFGGG4343"

private const val MINUTES_IN_STRING: String = "15"

class AddModelTest {

    private lateinit var model: AddModel

    @Before
    fun setUp() {
        model = AddModel()
    }

    @Test
    fun testSetStartDate() {
        model.setDate(START_DATE_EVENT, YEAR, MONTH, DAY)
        Assert.assertEquals(DATE, model.startDate)
        Assert.assertEquals(EMPTY_STRING, model.endDate)
    }

    @Test
    fun testSetStartTime() {
        model.setTime(START_TIME_EVENT, HOUR_OF_DAY, START_MINUTE)
        Assert.assertEquals(START_TIME, model.startTime)
        Assert.assertEquals(EMPTY_STRING, model.endTime)
    }

    @Test
    fun testSetEndDate() {
        model.setDate(END_DATE_EVENT, YEAR, MONTH, DAY)
        Assert.assertEquals(EMPTY_STRING, model.startDate)
        Assert.assertEquals(DATE, model.endDate)
    }

    @Test
    fun testSetEndTime() {
        model.setTime(END_TIME_EVENT, HOUR_OF_DAY, END_MINUTE)
        Assert.assertEquals(EMPTY_STRING, model.startTime)
        Assert.assertEquals(END_TIME, model.endTime)
    }

    @Test
    fun testSetInvalidDateType() {
        model.setDate(INVALID_TYPE, YEAR, MONTH, DAY)
        Assert.assertEquals(EMPTY_STRING, model.startDate)
        Assert.assertEquals(EMPTY_STRING, model.endDate)
    }

    @Test
    fun testSetInvalidTimeType() {
        model.setTime(INVALID_TYPE, HOUR_OF_DAY, END_MINUTE)
        Assert.assertEquals(EMPTY_STRING, model.startTime)
        Assert.assertEquals(EMPTY_STRING, model.endTime)
    }

    @Test
    @Throws(ParseException::class)
    fun testHasValidDateTimes() {
        setValidDatesTimes()
        model.setTime(END_TIME_EVENT, HOUR_OF_DAY, END_MINUTE)
        Assert.assertTrue(model.hasValidDateTimes())
        Assert.assertEquals(DateUtil.parseDateTime(model.startDate, model.startTime), model.startDateTime)
        Assert.assertEquals(DateUtil.parseDateTime(model.endDate, model.endTime), model.endDateTime)
    }

    @Test
    @Throws(ParseException::class)
    fun testHasInvalidDateTimes() {
        setValidDatesTimes()
        model.setTime(END_TIME_EVENT, HOUR_OF_DAY, INVALID_END_MINUTE)
        Assert.assertFalse(model.hasValidDateTimes())
        Assert.assertEquals(DateUtil.parseDateTime(model.startDate, model.startTime), model.startDateTime)
        Assert.assertEquals(DateUtil.parseDateTime(model.endDate, model.endTime), model.endDateTime)
    }

    @Test(expected = ParseException::class)
    @Throws(ParseException::class)
    fun testHasInvalidDateTimesCannotParse() {
        setValidDatesTimes()
        model.setTime(INVALID_TYPE, HOUR_OF_DAY, END_MINUTE)
        model.hasValidDateTimes()
    }

    private fun setValidDatesTimes() {
        model.setDate(START_DATE_EVENT, YEAR, MONTH, DAY)
        model.setTime(START_TIME_EVENT, HOUR_OF_DAY, START_MINUTE)
        model.setDate(END_DATE_EVENT, YEAR, MONTH, DAY)
    }

    @Test
    @Throws(ParseException::class, CloneNotSupportedException::class)
    fun testClone() {
        setValidDatesTimes()
        model.setTime(END_TIME_EVENT, HOUR_OF_DAY, END_MINUTE)
        model.hasValidDateTimes()
        Assert.assertEquals(model, model.clone())
    }

    @Test
    fun testSetTitle() {
        model.title = TEXT
        Assert.assertEquals(TEXT, model.title)
    }

    @Test
    fun testSetLocation() {
        model.location = TEXT
        Assert.assertEquals(TEXT, model.location)
    }

    @Test
    fun testSetDescription() {
        model.description = TEXT
        Assert.assertEquals(TEXT, model.description)
    }

    @Test
    fun testSetReminderText() {
        model.minutesReminderText = MINUTES_IN_STRING
        Assert.assertEquals(MINUTES_IN_STRING, model.minutesReminderText)
    }

    @Test
    fun testGetReminderWithoutMinutes() {
        model.minutesReminderText = TEXT
        Assert.assertEquals(0, model.minutesReminder.toLong())
    }

    @Test
    fun testGetReminderWithMinutes() {
        model.minutesReminderText = MINUTES_IN_STRING
        Assert.assertEquals(MINUTES_IN_STRING.toLong(), model.minutesReminder.toLong())
    }
}