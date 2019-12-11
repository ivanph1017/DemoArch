package com.eveexite.demoarch.calendar_qa_ft.presentation.add.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eveexite.demoarch.calendar_qa_ft.R
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.model.AddModel
import java.text.ParseException

class AddViewModel(private val model: AddModel): ViewModel() {

    private val liveData: MutableLiveData<AddModel> = MutableLiveData()
    private val errorMessageRes: MutableLiveData<Int> = MutableLiveData()
    private val errorMessageResException: MutableLiveData<Pair<Int, Exception>> = MutableLiveData()
    private val askPermission: MutableLiveData<AddModel> = MutableLiveData()

    init {
        liveData.postValue(model)
    }

    fun getLiveData(): LiveData<AddModel> = liveData

    fun getErrorMessageRes(): LiveData<Int> = errorMessageRes

    fun getErrorMessageResException(): LiveData<Pair<Int, Exception>> = errorMessageResException

    fun getAskPermission(): LiveData<AddModel> = askPermission

    fun setDate(type: Int, year: Int, month: Int, day: Int) {
        model.setDate(type, year, month, day)
        liveData.postValue(model)
    }

    fun setTime(type: Int, hourOfDay: Int, minute: Int) {
        model.setTime(type, hourOfDay, minute)
        liveData.postValue(model)
    }

    fun addEvent() {
        try {
            if (model.hasValidDateTimes()) {
                askPermission.postValue(model.clone())
            } else {
                errorMessageRes.postValue(R.string.invalid_dates_error_message)
            }
        } catch (exception: ParseException) {
            errorMessageResException.postValue(Pair(R.string.date_time_poorly_written, exception))
        } catch (exception: CloneNotSupportedException) {
            errorMessageResException.postValue(Pair(R.string.input_data_could_not_processed, exception))
        }
    }

}