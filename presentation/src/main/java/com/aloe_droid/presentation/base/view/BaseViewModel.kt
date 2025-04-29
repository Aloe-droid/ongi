package com.aloe_droid.presentation.base.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

abstract class BaseViewModel<UiState : UiContract.State, UiEvent : UiContract.Event, UiEffect : UiContract.SideEffect>(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState by lazy { MutableStateFlow(initState(savedStateHandle)) }
    val uiState by lazy { _uiState.asStateFlow() }
    val currentState: UiState
        get() = uiState.value

    private val _uiEffect: Channel<UiEffect> = Channel(Channel.BUFFERED)
    val uiEffect: Flow<UiEffect> = _uiEffect.receiveAsFlow()

    fun sendEvent(event: UiEvent) = runCatching { handleEvent(event) }
        .onFailure { throwable: Throwable -> handleError(throwable) }

    protected fun sendSideEffect(uiEffect: UiEffect) {
        val result: ChannelResult<Unit> = _uiEffect.trySend(uiEffect)
        if (result.isFailure) Timber.e(result.exceptionOrNull())
    }

    protected fun updateState(function: (UiState) -> UiState) {
        _uiState.update(function = function)
    }

    protected fun <Result> CoroutineScope.safeLaunch(
        onSuccess: (Result) -> Unit = {},
        block: suspend () -> Result
    ) {
        viewModelScope.launch {
            runCatching { block() }
                .onSuccess { result: Result -> onSuccess(result) }
                .onFailure { throwable: Throwable -> handleError(throwable = throwable) }
        }
    }

    protected suspend fun <T> Flow<T>.safeCollect(collector: suspend (T) -> Unit) =
        retryWhen { throwable: Throwable, attempt: Long ->
            handleRetry(throwable = throwable, attempt = attempt)
        }.catch { throwable: Throwable ->
            handleError(throwable = throwable)
        }.collect { uiState: T ->
            collector(uiState)
        }

    protected open fun handleError(throwable: Throwable) {
        Timber.e(t = throwable)
    }

    protected open suspend fun handleRetry(throwable: Throwable, attempt: Long): Boolean {
        if (attempt >= MAX_RETRY) return false
        if (throwable !is IOException) return false

        delay(RETRY_INTERVAL)
        return true
    }

    abstract fun initState(savedStateHandle: SavedStateHandle): UiState

    abstract fun handleEvent(event: UiEvent)

    companion object {
        private const val MAX_RETRY: Int = 2
        private const val RETRY_INTERVAL: Long = 1_000L
    }
}
