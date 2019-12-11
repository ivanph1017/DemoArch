package com.eveexite.demoarch.calendar_qa_ft.presentation.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun getFormattedDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        val locale = Locale(DateConstant.locale)
        calendar[year, month] = day
        val formatter = SimpleDateFormat(DateConstant.dateFormat, locale)
        return formatter.format(calendar.time)
    }

    fun getFormattedTime(hourOfDay: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        val locale = Locale(DateConstant.locale)
        calendar[Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hourOfDay] = minute
        val formatter = SimpleDateFormat(DateConstant.timeFormat, locale)
        return formatter.format(calendar.time)
    }

    @Throws(ParseException::class)
    fun parseDateTime(date: String, time: String): Calendar {
        val locale = Locale(DateConstant.locale)
        val formatter = SimpleDateFormat(DateConstant.dateTimeFormat, locale)
        val dateTime = String.format(DateConstant.dateTimeTemplate, date, time)
        val calendar = Calendar.getInstance()
        calendar.time = formatter.parse(dateTime) ?: throw ParseException("cannot parse date time", 0)
        return calendar
    }
}