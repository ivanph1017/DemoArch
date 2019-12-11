package com.eveexite.demoarch.calendar_qa_ft.presentation.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val LOCALE_TEXT: String = "es_PE"

private const val DATE_FORMAT: String = "dd/MM/yyyy"

private const val TIME_FORMAT: String = "hh:mm aa"

private const val DATE_TIME_TEMPLATE: String = "%s %s"

private val DATE_TIME_FORMAT: String = String.format(
    DATE_TIME_TEMPLATE,
    DATE_FORMAT,
    TIME_FORMAT
)

private val LOCALE = Locale(LOCALE_TEXT)

object DateUtil {

    fun getFormattedDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val formatter = SimpleDateFormat(DATE_FORMAT, LOCALE)
        return formatter.format(calendar.time)
    }

    fun getFormattedTime(hourOfDay: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hourOfDay, minute)
        val formatter = SimpleDateFormat(TIME_FORMAT, LOCALE)
        return formatter.format(calendar.time)
    }

    @Throws(ParseException::class)
    fun parseDateTime(date: String, time: String): Date {
        val formatter = SimpleDateFormat(DATE_TIME_FORMAT, LOCALE)
        val dateTime = String.format(DATE_TIME_TEMPLATE, date, time)
        val millis = formatter.parse("15/08/2019 09:59 PM").time
        return formatter.parse(dateTime) ?: throw ParseException("cannot parse date time", 0)
    }

    fun mapDateToCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance(LOCALE)
        calendar.time = date
        return calendar
    }
}