package com.eveexite.demoarch.calendar_qa_ft.presentation.add.view

import android.content.Context
import android.text.Editable
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.eveexite.demoarch.calendar_qa_ft.R
import com.google.android.material.textfield.TextInputLayout
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.lang.ref.WeakReference

@RunWith(AndroidJUnit4::class)
class TextListenerTest {

    @Rule
    @JvmField
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var textListener: TextListener

    @Mock
    private lateinit var textInputLayout: TextInputLayout

    @Mock
    private lateinit var listener: Listener

    @Mock
    private lateinit var editable: Editable

    private val context: Context = InstrumentationRegistry.getInstrumentation().context

    private val zeroCharacters: Int = 0

    private val nineCharacters: Int = 9

    @Before
    fun setUp() {
        textListener = TextListener(context, textInputLayout, WeakReference(listener))
    }

    @Test
    fun testAfterTextChangedEmptyEditable() {
        BDDMockito.given(editable.length).willReturn(zeroCharacters)
        textListener.afterTextChanged(editable)
        BDDMockito.then(listener).should().hideKeyword()
        BDDMockito.then(listener).should().validateEnablingButton()
    }

    @Test
    fun testAfterTextChangedExceedMaxCounter() {
        val maxLenght = 5
        BDDMockito.given(editable.length).willReturn(nineCharacters)
        BDDMockito.given(textInputLayout.isCounterEnabled).willReturn(true)
        BDDMockito.given(textInputLayout.counterMaxLength).willReturn(maxLenght)
        textListener.afterTextChanged(editable)
        BDDMockito.then(listener).should().validateEnablingButton()
    }

    @Test
    fun testOnTextInputLayoutEmptyError() {
        textListener.onTextInputLayoutError(R.string.field_empty_error_message)
        BDDMockito.then(listener).shouldHaveZeroInteractions()
    }

    @Test
    fun testOnTextInputLayoutExceedMaxCounterError() {
        textListener.onTextInputLayoutError(R.string.field_max_characters_error_message)
        BDDMockito.then(listener).shouldHaveZeroInteractions()
    }

    @Test
    fun testAfterTextChangedLessThanMaxCounter() {
        val maxLenght = 10
        BDDMockito.given(editable.length).willReturn(nineCharacters)
        BDDMockito.given(textInputLayout.isCounterEnabled).willReturn(true)
        BDDMockito.given(textInputLayout.counterMaxLength).willReturn(maxLenght)
        textListener.afterTextChanged(editable)
        BDDMockito.then(textInputLayout).should().isErrorEnabled = false
        BDDMockito.then(textInputLayout).should().setEndIconDrawable(R.drawable.ic_check)
        BDDMockito.then(listener).should().validateEnablingButton()
    }

    @Test
    fun testAfterTextChangedWithoutCounter() {
        BDDMockito.given(editable.length).willReturn(nineCharacters)
        BDDMockito.given(textInputLayout.isCounterEnabled).willReturn(false)
        textListener.afterTextChanged(editable)
        BDDMockito.then(textInputLayout).should().isErrorEnabled = false
        BDDMockito.then(textInputLayout).should().setEndIconDrawable(R.drawable.ic_check)
        BDDMockito.then(listener).should().validateEnablingButton()
    }
}