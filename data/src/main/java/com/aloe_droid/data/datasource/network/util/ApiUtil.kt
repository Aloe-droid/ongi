package com.aloe_droid.data.datasource.network.util

import com.aloe_droid.data.datasource.dto.ErrorDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal object ApiUtil {
    private const val EMPTY = "내용이 비어있습니다."
    private const val UNKNOWN = "일시적인 오류가 발생했습니다. 잠시 뒤에 다시 시도해주세요."
    private const val TIME_OUT = "서버 응답이 지연되고 있어요. 잠시 후 다시 시도해주세요."
    private const val UNKNOWN_HOST = "인터넷 연결을 확인해주세요. 서버에 연결할 수 없어요."
    private const val CONNECT_FAIL = "서버에 접속할 수 없어요. 네트워크 상태를 확인해주세요."

    private fun <T> Response<T>.toApiResult(): ApiResult<T> {
        return if (isSuccessful) {
            body()?.let { ApiResult.Success(it) } ?: ApiResult.Failure(RuntimeException(EMPTY))
        } else {
            val error: String = errorBody()?.string().toString()
            runCatching { Json.decodeFromString<ErrorDTO>(error) }
                .fold(
                    onSuccess = { errorEntity -> ApiResult.Failure(RuntimeException(errorEntity.message)) },
                    onFailure = { ApiResult.Failure(RuntimeException(UNKNOWN)) }
                )
        }
    }

    private fun handleError(error: Throwable): ApiResult.Failure {
        val errorMessage: String = when (error) {
            is SocketTimeoutException -> TIME_OUT
            is UnknownHostException -> UNKNOWN_HOST
            is ConnectException -> CONNECT_FAIL
            else -> UNKNOWN
        }
        return ApiResult.Failure(IOException(errorMessage))
    }

    private suspend fun <T> safeApiCall(call: suspend () -> Response<T>): ApiResult<T> {
        return runCatching { call() }
            .fold(
                onSuccess = { result: Response<T> -> result.toApiResult() },
                onFailure = { error: Throwable -> handleError(error) }
            )
    }

    fun <T> safeApiCallToFlow(call: suspend () -> Response<T>): Flow<T> = flow {
        val result: ApiResult<T> = safeApiCall { call() }
        when (result) {
            is ApiResult.Success -> emit(result.data)
            is ApiResult.Failure -> throw result.throwable
        }
    }
}
