package com.eveexite.demoarch.calendar_qa_ft.presentation.add.model

import com.eveexite.demoarch.calendar_qa_ft.presentation.add.*
import com.eveexite.demoarch.calendar_qa_ft.presentation.util.DateUtil
import java.text.ParseException
import java.util.*

data class AddModel(
    var title: String = EMPTY_STRING,
    var location: String = EMPTY_STRING,
    var description: String = EMPTY_STRING,
    var startDate: String = EMPTY_STRING,
    var startTime: String = EMPTY_STRING,
    var endDate: String = EMPTY_STRING,
    var endTime: String = EMPTY_STRING,
    var startDateTime: Date = Date(),
    var endDateTime: Date = Date(),
    var minutesReminderText: String = EMPTY_STRING
): Cloneable {

    var minutesReminder = 0
        get() {
            return try {
                minutesReminderText.toInt()
            } catch (exception: NumberFormatException) {
                0
            }
        }
        private set

    fun setDate(type: Int, year: Int, month: Int, day: Int) {
        when(type) {
            START_DATE_EVENT -> startDate = DateUtil.getFormattedDate(year, month, day)
            END_DATE_EVENT -> endDate = DateUtil.getFormattedDate(year, month, day)
        }
    }

    fun setTime(type: Int, hourOfDay: Int, minute: Int) {
        when(type) {
            START_TIME_EVENT -> startTime = DateUtil.getFormattedTime(hourOfDay, minute)
            END_TIME_EVENT -> endTime = DateUtil.getFormattedTime(hourOfDay, minute)
        }
    }

    @Throws(ParseException::class)
    fun hasValidDateTimes(): Boolean {
        startDateTime = DateUtil.parseDateTime(startDate, startTime)
        endDateTime = DateUtil.parseDateTime(endDate, endTime)
        return endDateTime.after(startDateTime)
    }

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): AddModel {
        val model = super.clone() as AddModel
        model.startDateTime = startDateTime.clone() as Date
        model.endDateTime = endDateTime.clone() as Date
        return model
    }

    override fun equals(other: Any?): Boolean {
        if (other !is AddModel) {
            return false
        }
        return title == other.title
                && location == other.location
                && description == other.description
                && startDate == other.startDate
                && startTime == other.startTime
                && endDate == other.endDate
                && endTime == other.endTime
                && startDateTime == other.startDateTime
                && endDateTime == other.endDateTime
                && minutesReminder == other.minutesReminder
                && minutesReminderText == other.minutesReminderText
    }

    override fun hashCode(): Int {
        var hash = 17
        hash *= 31 + title.hashCode()
        hash *= 31 + location.hashCode()
        hash *= 31 + description.hashCode()
        hash *= 31 + startDate.hashCode()
        hash *= 31 + startTime.hashCode()
        hash *= 31 + endDate.hashCode()
        hash *= 31 + endTime.hashCode()
        hash *= 31 + startDateTime.hashCode()
        hash *= 31 + endDateTime.hashCode()
        hash *= 31 + minutesReminder
        hash *= 31 + minutesReminderText.hashCode()
        return hash
    }

}