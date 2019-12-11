package com.eveexite.demoarch.calendar_qa_ft.presentation.finished

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import com.eveexite.demoarch.calendar_qa_ft.R
import com.eveexite.demoarch.calendar_qa_ft.databinding.ActivityFinishedBinding

class FinishedActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFinishedBinding>(this, R.layout.activity_finished)
        binding.btnBackToHome.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        NavUtils.navigateUpFromSameTask(this)
    }

}