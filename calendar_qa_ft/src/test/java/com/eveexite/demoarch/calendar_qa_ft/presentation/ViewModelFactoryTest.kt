package com.eveexite.demoarch.calendar_qa_ft.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.model.AddModel
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.viewmodel.AddViewModel
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ViewModelFactoryTest {

    @Rule
    @JvmField
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var model: AddModel

    private lateinit var viewModelFactory: ViewModelFactory

    @Before
    fun setUp() {
        viewModelFactory = ViewModelFactory(model)
    }

    @Test
    fun testCreate() {
        val viewModel: AddViewModel = viewModelFactory.create(AddViewModel::class.java)
        Assert.assertThat(viewModel, CoreMatchers.`is`(CoreMatchers.instanceOf<AddViewModel>(AddViewModel::class.java)))
    }
}