package com.eveexite.demoarch.core.device

import android.content.Context
import androidx.annotation.RawRes
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.*

class JsonUtil(
    private val context: Context,
    private val objectMapper: ObjectMapper
) {

    fun loadJson(@RawRes rawId: Int): String? {
        val inputStream: InputStream = context.resources.openRawResource(rawId)
        inputStream.use {
            val jsonNode: JsonNode = objectMapper.readTree(inputStream)
            val objectWriter = objectMapper.writerWithDefaultPrettyPrinter()
            return objectWriter.writeValueAsString(jsonNode)
        }
    }

    private inline fun <T : Closeable?> Array<T>.use(block: () -> Unit) {
        var exception: Throwable? = null
        try {
            return block()
        } catch (e: Throwable) {
            exception = e
            throw e
        } finally {
            when (exception == null) {
                true -> forEach { it?.close() }
                else -> forEach {
                    try {
                        it?.close()
                    } catch (closeException: Throwable) {
                        exception.addSuppressed(closeException)
                    }
                }
            }
        }
    }
}