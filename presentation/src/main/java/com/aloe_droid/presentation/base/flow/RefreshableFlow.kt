package com.aloe_droid.presentation.base.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

open class RefreshableFlow<T>(initState: T) {
    private val stateFlow: MutableStateFlow<T> = MutableStateFlow(initState)
    private val refreshFlow: MutableSharedFlow<Unit> = MutableSharedFlow()
    private val initRefresh: Flow<Unit> = refreshFlow.onStart { emit(Unit) }
    val flow: Flow<T> = combine(stateFlow, initRefresh) { state: T, _: Unit ->
        state
    }

    fun update(function: (T) -> T) = stateFlow.update(function = function)
    suspend fun refresh() = refreshFlow.emit(Unit)
}
