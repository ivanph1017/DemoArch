package com.eveexite.demoarch.calendar_qa_ft.device

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import com.eveexite.demoarch.calendar_qa_ft.R
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.model.AddModel
import java.lang.ref.WeakReference

private const val SELECT_CALENDAR_TOKEN: Int = 10
private const val ADD_EVENT_TOKEN: Int = 20
private const val ADD_REMINDER_TOKEN: Int = 30
private const val PROJECTION_ID_INDEX: Int = 0

class CalendarHandler(
    contentResolver: ContentResolver,
    private val weakListener: WeakReference<OnCompleteListener>
): AsyncQueryHandler(contentResolver) {

    private val calendarProjection: Array<String> = arrayOf(CalendarContract.Calendars._ID)

    fun selectCalendar() {
        startQuery(
            SELECT_CALENDAR_TOKEN,
            Any(),
            CalendarContract.Calendars.CONTENT_URI,
            calendarProjection,
            null,
            null,
            CalendarContract.Calendars._ID)
    }

    fun addEventToCalendar(model: AddModel, calendarId: Long) {
        val values = ContentValues()
        values.put(CalendarContract.Events.CALENDAR_ID, calendarId)
        values.put(CalendarContract.Events.TITLE, model.title)
        values.put(CalendarContract.Events.EVENT_LOCATION, model.location)
        values.put(CalendarContract.Events.DESCRIPTION, model.description)
        values.put(CalendarContract.Events.DTSTART, model.startDateTime.timeInMillis)
        values.put(CalendarContract.Events.DTEND, model.endDateTime.timeInMillis)
        values.put(CalendarContract.Events.EVENT_TIMEZONE, model.startDateTime.timeZone.id)
        startInsert(ADD_EVENT_TOKEN, Any(), CalendarContract.Events.CONTENT_URI, values)
    }

    fun addReminderToEvent(model: AddModel, eventId: Long) {
        val values = ContentValues()
        values.put(CalendarContract.Reminders.MINUTES, model.minutesReminder)
        values.put(CalendarContract.Reminders.EVENT_ID, eventId)
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        startInsert(ADD_REMINDER_TOKEN, Any(), CalendarContract.Reminders.CONTENT_URI, values)
    }

    override fun onQueryComplete(token: Int, cookie: Any, cursor: Cursor) {
        super.onQueryComplete(token, cookie, cursor)
        val isSelected: Boolean = token == SELECT_CALENDAR_TOKEN && cursor.moveToFirst()
        if (isSelected) {
            val calendarId = cursor.getLong(PROJECTION_ID_INDEX)
            weakListener.get()?.onCalendarQueryComplete(calendarId)
            return
        }
        weakListener.get()?.onError(R.string.calendar_could_not_be_selected)
    }

    override fun onInsertComplete(token: Int, cookie: Any, uri: Uri) {
        super.onInsertComplete(token, cookie, uri)
        val isEventAdded: Boolean = token == ADD_EVENT_TOKEN && uri.lastPathSegment != null
        val isReminderAdded: Boolean = token == ADD_REMINDER_TOKEN && uri.lastPathSegment != null
        when {
            isEventAdded -> {
                try {
                    val eventId: Long? = uri.lastPathSegment?.toLong()
                    eventId?. let { weakListener.get()?.onEventInsertComplete(it) }
                } catch (exception: NumberFormatException) {
                    weakListener.get()?.onError(R.string.event_id_could_not_be_parse, exception)
                }
            }
            isReminderAdded -> weakListener.get()?.onReminderInsertComplete()
            token == ADD_EVENT_TOKEN -> weakListener.get()?.onError(
                R.string.event_could_not_be_added
            )
            token == ADD_REMINDER_TOKEN -> weakListener.get()?.onError(
                R.string.reminder_could_not_be_added
            )
        }
    }
}