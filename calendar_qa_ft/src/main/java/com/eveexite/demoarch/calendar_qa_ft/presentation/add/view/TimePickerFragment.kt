package com.eveexite.demoarch.calendar_qa_ft.presentation.add.view

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.viewmodel.AddViewModel
import com.eveexite.demoarch.calendar_qa_ft.presentation.ViewModelFactory
import java.util.*

class TimePickerFragment internal constructor(
    private val viewModelFactory: ViewModelFactory,
    private val timeType: Int
): DialogFragment(), TimePickerDialog.OnTimeSetListener {

    // Create a new instance of TimePickerDialog and return it
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val fragmentActivity: FragmentActivity = activity ?: throw InstantiationException("Activity is null", null)
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]
        val is24HourFormat: Boolean = DateFormat.is24HourFormat(fragmentActivity)
        return TimePickerDialog(
            fragmentActivity,
            this,
            hour,
            minute,
            is24HourFormat
        )
    }

    // Do something with the time chosen by the user
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        activity?.let {
            val viewModel = ViewModelProviders.of(it, viewModelFactory).get(AddViewModel::class.java)
            viewModel.setTime(timeType, hourOfDay, minute)
        }
    }
}