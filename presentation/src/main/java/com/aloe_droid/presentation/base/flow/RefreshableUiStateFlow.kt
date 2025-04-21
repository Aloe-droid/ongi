package com.aloe_droid.presentation.base.flow

import com.aloe_droid.presentation.base.UiContract
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

open class RefreshableUiStateFlow<UiState>(initState: UiContract.State<UiState>) {
    private val uiStateFlow = MutableStateFlow<UiContract.State<UiState>>(initState)
    private val refreshFlow = MutableSharedFlow<Unit>()
    private val initRefresh = refreshFlow.onStart { emit(Unit) }
    val flow = combine(uiStateFlow, initRefresh) { uiState: UiContract.State<UiState>, _: Unit ->
        uiState
    }

    fun update(reduce: (UiContract.State<UiState>) -> UiContract.State<UiState>) = uiStateFlow.update(reduce)
    suspend fun refresh() = refreshFlow.emit(Unit)
}