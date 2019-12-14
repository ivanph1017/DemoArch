package com.eveexite.demoarch.calendar_qa_ft

import com.eveexite.demoarch.core.CoreApp
import com.eveexite.demoarch.core.DaggerCoreComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class CalendarAppTest: CoreApp() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        coreComponent = DaggerCoreComponent.factory().create(this)
        return coreComponent
    }
}