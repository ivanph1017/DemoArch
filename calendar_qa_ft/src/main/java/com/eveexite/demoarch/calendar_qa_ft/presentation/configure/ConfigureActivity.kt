package com.eveexite.demoarch.calendar_qa_ft.presentation.configure

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eveexite.demoarch.calendar_qa_ft.R
import com.eveexite.demoarch.calendar_qa_ft.databinding.ActivityConfigureBinding
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.view.AddActivity

class ConfigureActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityConfigureBinding>(this, R.layout.activity_configure)
        binding.btnConfigure.setOnClickListener { startActivity(Intent(this, AddActivity::class.java)) }
    }
}