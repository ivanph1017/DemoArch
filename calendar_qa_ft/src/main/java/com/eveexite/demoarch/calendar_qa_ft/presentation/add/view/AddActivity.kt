package com.eveexite.demoarch.calendar_qa_ft.presentation.add.view

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.eveexite.demoarch.calendar_qa_ft.DaggerFeatureComponent
import com.eveexite.demoarch.calendar_qa_ft.R
import com.eveexite.demoarch.calendar_qa_ft.databinding.ActivityAddBinding
import com.eveexite.demoarch.calendar_qa_ft.device.CalendarHandler
import com.eveexite.demoarch.calendar_qa_ft.device.OnCompleteListener
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.*
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.model.AddModel
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.viewmodel.AddViewModel
import com.eveexite.demoarch.calendar_qa_ft.presentation.ViewModelFactory
import com.eveexite.demoarch.calendar_qa_ft.presentation.finished.FinishedActivity
import com.eveexite.demoarch.core.CoreComponent
import com.eveexite.demoarch.core.CoreComponentProvider
import com.eveexite.demoarch.core.presentation.CoreBaseActivity
import com.google.android.material.textfield.TextInputLayout
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.lang.ref.WeakReference

@LayoutRes private val LAYOUT: Int = R.layout.activity_add

private const val READ_WRITE_CALENDAR_PERM = 100

class AddActivity: CoreBaseActivity<ViewModelFactory, ActivityAddBinding>(), OnCompleteListener, Listener {

    private val readWriteCalendar: Array<String> = arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)

    private lateinit var viewModel: AddViewModel

    private var calendarHandler: CalendarHandler? = null

    private var model: AddModel? = null

    override fun getLayout(): Int = LAYOUT

    override fun initDependencyInjection() {
        val coreComponentProvider = application as CoreComponentProvider
        val coreComponent: CoreComponent = coreComponentProvider.provideCoreComponent()
        DaggerFeatureComponent.factory()
            .create(coreComponent)
            .inject(this)
        runOnUiThread { initView() }
    }

    override fun initView() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = ArrayAdapter(this,
            R.layout.dropdown_menu_popup_item,
            resources.getStringArray(R.array.minutes))
        binding.actvReminder.setAdapter(adapter)
        setUpTextChangedListeners()
        setUpOnFocusListeners()
        hideSoftInputOnFocus()
        validateEnablingButton()
        setUpObservers()
        calendarHandler = CalendarHandler(contentResolver, WeakReference(this))
    }

    private fun setUpTextChangedListeners() {
        binding.etTitle.addTextChangedListener(createTextListener(binding.layoutTitle))
        binding.etLocation.addTextChangedListener(createTextListener(binding.layoutLocation))
        binding.etDescription.addTextChangedListener(createTextListener(binding.layoutDescription))
        binding.etStartDate.addTextChangedListener(createTextListener(binding.layoutStartDate))
        binding.etStartTime.addTextChangedListener(createTextListener(binding.layoutStartTime))
        binding.etEndDate.addTextChangedListener(createTextListener(binding.layoutEndDate))
        binding.etEndTime.addTextChangedListener(createTextListener(binding.layoutEndTime))
    }

    private fun createTextListener(textInputLayout: TextInputLayout)
            = TextListener(textInputLayout, WeakReference(this))

    private fun setUpOnFocusListeners() {
        binding.etStartDate.onFocusChangeListener = createOnFocusChangeListener(START_DATE_EVENT)
        binding.etStartTime.onFocusChangeListener = createOnFocusChangeListener(START_TIME_EVENT)
        binding.etEndDate.onFocusChangeListener = createOnFocusChangeListener(END_DATE_EVENT)
        binding.etEndTime.onFocusChangeListener = createOnFocusChangeListener(END_TIME_EVENT)
    }

    private fun createOnFocusChangeListener(type: Int) = OnFocusChangeListener {
            _: View, hasFocus: Boolean ->
        when (type) {
            START_DATE_EVENT, END_DATE_EVENT -> showDatePickerDialog(hasFocus, type)
            START_TIME_EVENT, END_TIME_EVENT -> showTimePickerDialog(hasFocus, type)
        }
    }

    private fun hideSoftInputOnFocus() {
        binding.etStartDate.showSoftInputOnFocus = false
        binding.etStartTime.showSoftInputOnFocus = false
        binding.etEndDate.showSoftInputOnFocus = false
        binding.etEndTime.showSoftInputOnFocus = false
        binding.actvReminder.showSoftInputOnFocus = false
    }

    override fun validateEnablingButton() {
        binding.btnAdd.isEnabled = areFilledFields() && areValidFieldLengths()
    }

    override fun onTextInputLayoutError(textInputLayout: TextInputLayout, errorMessageRes: Int) {
        textInputLayout.isErrorEnabled = true
        textInputLayout.error = getString(errorMessageRes)
        textInputLayout.setEndIconDrawable(R.drawable.ic_exclamation)
    }

    override fun hideKeyword() {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun areFilledFields(): Boolean {
        return !TextUtils.isEmpty(binding.etTitle.text)
                && !TextUtils.isEmpty(binding.etLocation.text)
                && !TextUtils.isEmpty(binding.etDescription.text)
                && !TextUtils.isEmpty(binding.etStartDate.text)
                && !TextUtils.isEmpty(binding.etStartTime.text)
                && !TextUtils.isEmpty(binding.etEndDate.text)
                && !TextUtils.isEmpty(binding.etEndTime.text)
    }

    private fun areValidFieldLengths(): Boolean {
        val editableTitle: Editable? = binding.etTitle.text
        val editableLocation: Editable? = binding.etLocation.text
        val editableDescription: Editable? = binding.etDescription.text
        return editableTitle != null
                && binding.layoutTitle.isCounterEnabled
                && editableTitle.length <= binding.layoutTitle.counterMaxLength
                && editableLocation != null
                && binding.layoutLocation.isCounterEnabled
                && editableLocation.length <= binding.layoutLocation.counterMaxLength
                && editableDescription != null
                && binding.layoutDescription.isCounterEnabled
                && editableDescription.length <= binding.layoutDescription.counterMaxLength
    }

    private fun showDatePickerDialog(hasFocus: Boolean, dateEvent: Int) {
        if (hasFocus) {
            val newFragment: DialogFragment = DatePickerFragment(viewModelFactory, dateEvent)
            newFragment.show(supportFragmentManager, EMPTY_STRING)
        }
    }

    private fun showTimePickerDialog(hasFocus: Boolean, timeEvent: Int) {
        if (hasFocus) {
            val newFragment: DialogFragment = TimePickerFragment(viewModelFactory, timeEvent)
            newFragment.show(supportFragmentManager, EMPTY_STRING)
        }
    }

    private fun setUpObservers() {
        viewModel.getErrorMessageRes().observe(this, errorMsgResChanges)
        viewModel.getAskPermission().observe(this, askPermissionsChanges)
        viewModel.getErrorMessageResException().observe(this, errorMsgResExceptionChanges)
    }

    private val errorMsgResChanges = Observer<Int> {
        it?.let {
            observeErrorMessageRes(it)
        }
    }

    private val askPermissionsChanges = Observer<AddModel> {
        it?.let {
            observeAskPermission(it)
        }
    }

    private val errorMsgResExceptionChanges = Observer<Pair<Int, Exception>> {
        it?.let {
            observeErrorMessageResException(it)
        }
    }

    private fun observeErrorMessageRes(errorMessageRes: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.error)
        builder.setMessage(errorMessageRes)
        builder.setPositiveButton(R.string.ok) { _: DialogInterface, _: Int -> }
        builder.show()
    }

    private fun observeErrorMessageResException(pair: Pair<Int, Exception>) {
        val errorMessageRes: Int = pair.first
        val exception = pair.second
        Log.e("Error", getString(errorMessageRes), exception)
        observeErrorMessageRes(errorMessageRes)
    }

    private fun observeAskPermission(model: AddModel) {
        this.model = model
        validatePermissions()
    }

    @AfterPermissionGranted(READ_WRITE_CALENDAR_PERM)
    fun validatePermissions() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_CALENDAR)) {
            // Have permissions, select calendar
            calendarHandler?.selectCalendar()
            return
        }
        // Ask for Write Calendar permission
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.calendar_permission),
            READ_WRITE_CALENDAR_PERM,
            *readWriteCalendar)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String?>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onCalendarQueryComplete(calendarId: Long) {
        model?.let { calendarHandler?.addEventToCalendar(it, calendarId) }
    }

    override fun onEventInsertComplete(eventId: Long) {
        model?.let {
            if (it.minutesReminder > 0) {
                calendarHandler?.addReminderToEvent(it, eventId)
            } else {
                startActivity(Intent(this, FinishedActivity::class.java))
            }
        }
    }

    override fun onReminderInsertComplete() {
        startActivity(Intent(this, FinishedActivity::class.java))
    }

    override fun onError(errorMessageRes: Int) {
        observeErrorMessageRes(errorMessageRes)
    }

    override fun onError(errorMessageRes: Int, exception: Exception) {
        Log.e("Error", getString(errorMessageRes), exception)
        observeErrorMessageRes(errorMessageRes)
    }
}