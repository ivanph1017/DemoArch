package com.eveexite.demoarch.calendar_qa_ft

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class CalendarRunner: AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, AppTest::class.qualifiedName, context)
    }
}