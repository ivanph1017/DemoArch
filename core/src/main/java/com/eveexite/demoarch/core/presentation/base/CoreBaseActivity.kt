package com.eveexite.demoarch.core.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eveexite.demoarch.core.domain.JobExecutor
import javax.inject.Inject

abstract class CoreBaseActivity<T : ViewModelProvider.Factory>: AppCompatActivity() {

    @Inject
    protected lateinit var viewModelFactory: T

    override fun onCreate(savedInstanceState: Bundle?) {
        JobExecutor.execute { buildDaggerFeatureComponent() }
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    @LayoutRes protected abstract fun getLayout(): Int

    protected abstract fun buildDaggerFeatureComponent()
}