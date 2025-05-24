package com.aloe_droid.data.datasource.network.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

internal object FlowUtil {
    private const val MAX_RETRY: Int = 2
    private const val RETRY_INTERVAL: Long = 1_000L

    fun <T> Flow<T>.retryOnIOException() = retryWhen { throwable: Throwable, attempt: Long ->
        if (attempt >= MAX_RETRY) return@retryWhen false
        if (throwable !is IOException) return@retryWhen false

        delay(RETRY_INTERVAL)
        return@retryWhen true
    }
}
