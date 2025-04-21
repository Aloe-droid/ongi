package com.aloe_droid.presentation.base

import androidx.compose.runtime.Stable

interface UiContract {
    interface Route
    interface Event
    interface SideEffect

    @Stable
    sealed class State<out T> {
        data object Idle : State<Nothing>()
        data class Success<T>(val data: T) : State<T>()
        data class Loading<T>(val data: T? = null) : State<T>()
        data class Error<T>(val data: T? = null, val throwable: Throwable) : State<T>()
    }
}
