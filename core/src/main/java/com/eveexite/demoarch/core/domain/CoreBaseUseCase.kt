package com.eveexite.demoarch.core.domain

import java.lang.ref.WeakReference

abstract class CoreBaseUseCase<T: DomainErrorEventListener> {

    protected lateinit var weakListener: WeakReference<T>

    protected abstract fun getCodeBlock(): Runnable

    fun execute(weakListener: WeakReference<T>) {
        this.weakListener = weakListener
        JobExecutor.execute(getCodeBlock())
    }
}