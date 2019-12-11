package com.eveexite.demoarch.core.presentation

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.eveexite.demoarch.core.domain.JobExecutor
import javax.inject.Inject

abstract class CoreBaseActivity<T : ViewModelProvider.Factory, U : ViewDataBinding>: AppCompatActivity() {

    @Inject
    protected lateinit var viewModelFactory: T
    protected lateinit var binding: U

    override fun onCreate(savedInstanceState: Bundle?) {
        JobExecutor.execute { initDependencyInjection() }
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout())
    }

    @LayoutRes protected abstract fun getLayout(): Int

    protected abstract fun initDependencyInjection()

    protected abstract fun initView()
}