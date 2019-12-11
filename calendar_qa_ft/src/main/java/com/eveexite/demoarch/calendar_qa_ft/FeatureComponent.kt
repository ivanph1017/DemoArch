package com.eveexite.demoarch.calendar_qa_ft

import com.eveexite.demoarch.calendar_qa_ft.presentation.FeatureUiModule
import com.eveexite.demoarch.calendar_qa_ft.presentation.add.view.AddActivity
import com.eveexite.demoarch.core.CoreComponent
import com.eveexite.demoarch.core.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [
        FeatureUiModule::class
    ],
    dependencies = [
        CoreComponent::class
    ]
)
interface FeatureComponent {

    fun inject(addActivity: AddActivity)

    @Component.Factory
    interface Factory {

        fun create(coreComponent: CoreComponent): FeatureComponent
    }
}