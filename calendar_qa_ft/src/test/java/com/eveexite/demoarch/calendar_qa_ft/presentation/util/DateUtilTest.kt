package com.eveexite.demoarch.calendar_qa_ft.presentation.util

import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.text.ParseException
import java.util.*


private const val YEAR: Int = 2019

private const val MONTH: Int = 7

private const val DAY: Int = 15

private const val EXPECTED_DATE: String = "15/08/2019"

private const val HOUR_OF_DAY: Int = 21

private const val MINUTE: Int = 59

private const val EXPECTED_TIME: String = "09:59 PM"

private const val EXPECTED_MILLIS: Long = 1565924340000

@RunWith(MockitoJUnitRunner::class)
class DateUtilTest {

    @Test
    fun testGetFormattedDate() {
        val date: String = DateUtil.getFormattedDate(YEAR, MONTH, DAY)
        TestCase.assertEquals(EXPECTED_DATE, date)
    }

    @Test
    fun testGetFormattedTime() {
        val time: String = DateUtil.getFormattedTime(HOUR_OF_DAY, MINUTE)
        TestCase.assertEquals(EXPECTED_TIME, time)
    }

    @Test
    @Throws(ParseException::class)
    fun testParseDateTime() {
        val dateJava: Date = DateUtil.parseDateTime(EXPECTED_DATE, EXPECTED_TIME)

        TestCase.assertEquals(EXPECTED_MILLIS, dateJava.time)
        val calendarToTest: Calendar = DateUtil.mapDateToCalendar(dateJava)
        TestCase.assertEquals(YEAR, calendarToTest[Calendar.YEAR])
        TestCase.assertEquals(MONTH, calendarToTest[Calendar.MONTH])
        TestCase.assertEquals(DAY, calendarToTest[Calendar.DAY_OF_MONTH])
        TestCase.assertEquals(HOUR_OF_DAY, calendarToTest[Calendar.HOUR_OF_DAY])
        TestCase.assertEquals(MINUTE, calendarToTest[Calendar.MINUTE])
    }

    @Test
    fun testMapDateToCalendar() {
        val dateMock: Date = Mockito.mock(Date::class.java)
        BDDMockito.`when`(dateMock.time).thenReturn(EXPECTED_MILLIS)

        val calendarToTest: Calendar = DateUtil.mapDateToCalendar(dateMock)

        TestCase.assertEquals(YEAR, calendarToTest[Calendar.YEAR])
        TestCase.assertEquals(MONTH, calendarToTest[Calendar.MONTH])
        TestCase.assertEquals(DAY, calendarToTest[Calendar.DAY_OF_MONTH])
        TestCase.assertEquals(HOUR_OF_DAY, calendarToTest[Calendar.HOUR_OF_DAY])
        TestCase.assertEquals(MINUTE, calendarToTest[Calendar.MINUTE])
    }
}