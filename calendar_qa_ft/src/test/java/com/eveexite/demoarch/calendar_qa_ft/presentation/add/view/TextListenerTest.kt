package com.eveexite.demoarch.calendar_qa_ft.presentation.add.view

import android.text.Editable
import com.eveexite.demoarch.calendar_qa_ft.R
import com.google.android.material.textfield.TextInputLayout
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.ref.WeakReference

private const val ZERO_CHARACTERS: Int = 0

private const val NINE_CHARACTERS: Int = 9

@RunWith(MockitoJUnitRunner::class)
class TextListenerTest {

    private lateinit var textListener: TextListener

    @Mock
    private lateinit var textInputLayout: TextInputLayout

    @Mock
    private lateinit var listener: Listener

    @Mock
    private lateinit var editable: Editable

    @Before
    fun setUp() {
        textListener = TextListener(textInputLayout, WeakReference(listener))
    }

    @Test
    fun testAfterTextChangedEmptyEditable() {
        BDDMockito.given(editable.length).willReturn(ZERO_CHARACTERS)
        textListener.afterTextChanged(editable)
        BDDMockito.then(listener).should().onTextInputLayoutError(textInputLayout, R.string.field_empty_error_message)
        BDDMockito.then(listener).should().hideKeyword()
        BDDMockito.then(listener).should().validateEnablingButton()
    }

    @Test
    fun testAfterTextChangedExceedMaxCounter() {
        val maxLenght = 5
        BDDMockito.given(editable.length).willReturn(NINE_CHARACTERS)
        BDDMockito.given(textInputLayout.isCounterEnabled).willReturn(true)
        BDDMockito.given(textInputLayout.counterMaxLength).willReturn(maxLenght)
        textListener.afterTextChanged(editable)
        BDDMockito.then(listener).should().onTextInputLayoutError(textInputLayout, R.string.field_max_characters_error_message)
        BDDMockito.then(listener).should().validateEnablingButton()
    }

    @Test
    fun testAfterTextChangedLessThanMaxCounter() {
        val maxLenght = 10
        BDDMockito.given(editable.length).willReturn(NINE_CHARACTERS)
        BDDMockito.given(textInputLayout.isCounterEnabled).willReturn(true)
        BDDMockito.given(textInputLayout.counterMaxLength).willReturn(maxLenght)
        textListener.afterTextChanged(editable)
        BDDMockito.then(textInputLayout).should().isErrorEnabled = false
        BDDMockito.then(textInputLayout).should().setEndIconDrawable(R.drawable.ic_check)
        BDDMockito.then(listener).should().validateEnablingButton()
    }

    @Test
    fun testAfterTextChangedWithoutCounter() {
        BDDMockito.given(editable.length).willReturn(NINE_CHARACTERS)
        BDDMockito.given(textInputLayout.isCounterEnabled).willReturn(false)
        textListener.afterTextChanged(editable)
        BDDMockito.then(textInputLayout).should().isErrorEnabled = false
        BDDMockito.then(textInputLayout).should().setEndIconDrawable(R.drawable.ic_check)
        BDDMockito.then(listener).should().validateEnablingButton()
    }
}