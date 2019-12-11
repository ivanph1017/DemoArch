package com.eveexite.demoarch.calendar_qa_ft.presentation.add.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.viewmodel.AddViewModel
import com.eveexite.demoarch.calendar_qa_ft.presentation.ViewModelFactory
import java.util.*

class DatePickerFragment internal constructor(
    private val viewModelFactory: ViewModelFactory,
    private val dateType: Int
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    // Create a new instance of DatePickerDialog and return it
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val fragmentActivity: FragmentActivity = activity ?: throw InstantiationException("Activity is null", null)
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        val dialog = DatePickerDialog(fragmentActivity,
            this,
            year,
            month,
            day)
        dialog.datePicker.minDate = System.currentTimeMillis()
        return dialog
    }

    // Do something with the date chosen by the user
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        activity?.let {
            val viewModel = ViewModelProviders.of(it, viewModelFactory).get(AddViewModel::class.java)
            viewModel.setDate(dateType, year, month, day)
        }
    }
}