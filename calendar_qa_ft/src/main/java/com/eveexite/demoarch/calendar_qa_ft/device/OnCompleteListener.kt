package com.eveexite.demoarch.calendar_qa_ft.device

interface OnCompleteListener {

    fun onCalendarQueryComplete(calendarId: Long)

    fun onEventInsertComplete(eventId: Long)

    fun onReminderInsertComplete()

    fun onError(errorMessageRes: Int)

    fun onError(errorMessageRes: Int, exception: Exception)
}