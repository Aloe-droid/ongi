package com.aloe_droid.presentation.splash.contract.state

import androidx.compose.runtime.Stable
import com.aloe_droid.presentation.base.view.UiContract

@Stable
data class SplashUiState(
    val isInitState: Boolean = true,
    val isLoading: Boolean = true
) : UiContract.State
