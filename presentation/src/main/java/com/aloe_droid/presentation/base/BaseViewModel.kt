package com.aloe_droid.presentation.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aloe_droid.presentation.base.UiContract.Event
import com.aloe_droid.presentation.base.UiContract.SideEffect
import com.aloe_droid.presentation.base.UiContract.State
import com.aloe_droid.presentation.base.flow.RefreshableUiStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

abstract class BaseViewModel<UiState, UiEvent : Event, UiEffect : SideEffect>(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initState: State<UiState> by lazy { initialState(savedStateHandle) }
    private val _uiState = RefreshableUiStateFlow<UiState>(initState)
    val uiState: StateFlow<State<UiState>> = _uiState.flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_FLOW_TIME_OUT),
        initialValue = initState
    )

    private val _uiEffect: Channel<UiEffect> = Channel(Channel.BUFFERED)
    val uiEffect: Flow<UiEffect> = _uiEffect.receiveAsFlow()

    abstract fun initialState(savedStateHandle: SavedStateHandle): State<UiState>
    abstract fun handleEvent(event: UiEvent)

    fun sendEvent(event: UiEvent) = viewModelScope.safeLaunch {
        handleEvent(event)
    }

    protected fun updateState(reducer: (State<UiState>) -> State<UiState>) =
        _uiState.update(reduce = reducer)

    protected fun refreshState() = viewModelScope.safeLaunch {
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

    protected suspend fun Flow<UiState>.collectWithUiState() = onStart {
        handleLoading()
    }.retryWhen { throwable: Throwable, attempt: Long ->
        handleRetry(throwable = throwable, attempt = attempt)
    }.catch { throwable: Throwable ->
        handleError(throwable = throwable)
    }.collect { data: UiState ->
        handleSuccess(data = data)
    }

    private fun handleError(throwable: Throwable) {
        Timber.e(t = throwable)
        updateState { state: State<UiState> ->
            State.Error<UiState>(data = state.getData(), throwable = throwable)
        }
    }

    private fun handleLoading() = updateState { state: State<UiState> ->
        State.Loading<UiState>(data = state.getData())
    }

    private fun handleSuccess(data: UiState) = updateState {
        State.Success<UiState>(data = data)
    }

    private fun handleRetry(throwable: Throwable, attempt: Long): Boolean {
        if (attempt >= MAX_RETRY) return false
        if (throwable !is IOException) return false
        return true
    }

    private fun State<UiState>.getData(): UiState? = when (this) {
        is State.Idle -> null
        is State.Loading -> data
        is State.Success -> data
        is State.Error -> data
    }

    companion object {
        private const val MAX_RETRY: Int = 2
        private const val STATE_FLOW_TIME_OUT: Long = 5_000L
    }
}
