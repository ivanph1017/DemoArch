package com.eveexite.demoarch.core.domain

import androidx.annotation.NonNull
import java.util.concurrent.*

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor}
 */
object JobExecutor: Executor  {

    private const val idleCoresNumber: Int = 0
    private val maxCoresNumber: Int = Runtime.getRuntime().availableProcessors()
    private const val keepAliveTime: Long = 10
    private val keepAliveTimeUnit = TimeUnit.SECONDS

    private val threadPoolExecutor = ThreadPoolExecutor(
        idleCoresNumber,
        maxCoresNumber,
        keepAliveTime,
        keepAliveTimeUnit,
        LinkedBlockingQueue(),
        JobThreadFactory
    )

    override fun execute(runnable: Runnable) {
        threadPoolExecutor.execute(runnable)
    }

}
object JobThreadFactory: ThreadFactory {

    private var counter: Int = 0

    override fun newThread(@NonNull runnable: Runnable) = Thread(runnable, "android_thread_${counter++}")
}