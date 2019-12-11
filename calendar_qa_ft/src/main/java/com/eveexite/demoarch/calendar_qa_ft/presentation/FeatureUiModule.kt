package com.eveexite.demoarch.calendar_qa_ft.presentation

import com.eveexite.demoarch.calendar_qa_ft.presentation.add.model.AddModel
import com.eveexite.demoarch.core.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class FeatureUiModule {

    @FeatureScope
    @Provides
    fun provideAddModel() = AddModel()

    @FeatureScope
    @Provides
    fun provideViewModelFactory(addModel: AddModel) = ViewModelFactory(addModel)
}