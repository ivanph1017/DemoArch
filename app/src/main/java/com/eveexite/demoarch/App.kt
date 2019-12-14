package com.eveexite.demoarch

import android.content.Context
import com.eveexite.demoarch.core.CoreApp
import com.google.android.play.core.splitcompat.SplitCompat

open class App: CoreApp() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}