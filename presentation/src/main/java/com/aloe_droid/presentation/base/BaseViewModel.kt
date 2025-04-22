package com.aloe_droid.presentation.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aloe_droid.presentation.base.UiContract.Event
import com.aloe_droid.presentation.base.UiContract.SideEffect
import com.aloe_droid.presentation.base.UiContract.State
import com.aloe_droid.presentation.base.flow.RefreshableFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

abstract class BaseViewModel<UiState : State, UiEvent : Event, UiEffect : SideEffect>(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialState: UiState by lazy { initState(savedStateHandle) }
    private val _uiState by lazy { RefreshableFlow(initialState) }
    val uiState: StateFlow<UiState> by lazy {
        _uiState.flow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STATE_FLOW_TIME_OUT),
            initialValue = initialState
        )
    }

    val currentState: UiState get() = uiState.value

    private val _uiEffect: Channel<UiEffect> = Channel(Channel.BUFFERED)
    val uiEffect: Flow<UiEffect> = _uiEffect.receiveAsFlow()

    fun sendEvent(event: UiEvent) = viewModelScope.safeLaunch {
        handleEvent(event)
    }

    protected fun sendSideEffect(uiEffect: UiEffect) {
        val result: ChannelResult<Unit> = _uiEffect.trySend(uiEffect)
        if (result.isFailure) Timber.e(result.exceptionOrNull())
    }

    protected fun updateState(function: (UiState) -> UiState) {
        _uiState.update(function = function)
    }

    protected suspend fun refresh() {
        _uiState.refresh()
    }

    protected fun <Result> CoroutineScope.safeLaunch(
        onSuccess: (Result) -> Unit = {},
        block: suspend () -> Result
    ) = viewModelScope.launch {
        runCatching { block() }
            .onSuccess { result: Result -> onSuccess(result) }
            .onFailure { throwable: Throwable -> handleError(throwable = throwable) }
    }

    protected suspend fun <T> Flow<T>.safeCollect(collector: suspend (T) -> Unit) =
        retryWhen { throwable: Throwable, attempt: Long ->
            handleRetry(throwable = throwable, attempt = attempt)
        }.catch { throwable: Throwable ->
            handleError(throwable = throwable)
        }.collect { uiState: T ->
            collector(uiState)
        }

    open fun handleError(throwable: Throwable) {
        Timber.e(t = throwable)
    }

    open suspend fun handleRetry(throwable: Throwable, attempt: Long): Boolean {
        if (attempt >= MAX_RETRY) return false
        if (throwable !is IOException) return false

        delay(RETRY_INTERVAL)
        return true
    }

    abstract fun initState(savedStateHandle: SavedStateHandle): UiState

    abstract fun handleEvent(event: UiEvent)

    companion object {
        private const val MAX_RETRY: Int = 2
        private const val STATE_FLOW_TIME_OUT: Long = 5_000L
        private const val RETRY_INTERVAL: Long = 1_000L
    }
}
