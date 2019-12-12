package com.eveexite.demoarch.calendar_qa_ft.presentation.add.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.eveexite.demoarch.calendar_qa_ft.R
import com.google.android.material.textfield.TextInputLayout
import java.lang.ref.WeakReference

internal class TextListener(
    private val context: Context,
    private val textInputLayout: TextInputLayout,
    private val weakListener: WeakReference<Listener>
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        Log.d("AddEventToCalendar", "beforeTextChanged")
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.d("AddEventToCalendar", "onTextChanged")
    }

    override fun afterTextChanged(editable: Editable) {
        val isOverCounterMaxLength: Boolean = textInputLayout.isCounterEnabled
                && editable.length > textInputLayout.counterMaxLength
        when {
            editable.isEmpty() -> {
                onTextInputLayoutError(R.string.field_empty_error_message)
                weakListener.get()?.hideKeyword()
            }
            isOverCounterMaxLength -> onTextInputLayoutError(R.string.field_max_characters_error_message)
            else -> {
                textInputLayout.isErrorEnabled = false
                textInputLayout.setEndIconDrawable(R.drawable.ic_check)
            }
        }
        weakListener.get()?.validateEnablingButton()
    }

    internal fun onTextInputLayoutError(errorMessageRes: Int) {
        textInputLayout.isErrorEnabled = true
        textInputLayout.error = context.getString(errorMessageRes)
        textInputLayout.setEndIconDrawable(R.drawable.ic_exclamation)
    }

}

internal interface Listener {

    fun hideKeyword()

    fun validateEnablingButton()
}