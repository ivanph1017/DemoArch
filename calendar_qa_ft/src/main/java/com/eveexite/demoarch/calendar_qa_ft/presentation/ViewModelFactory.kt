package com.eveexite.demoarch.calendar_qa_ft.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.model.AddModel
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.viewmodel.AddViewModel

class ViewModelFactory(private val model: AddModel): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(model) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}