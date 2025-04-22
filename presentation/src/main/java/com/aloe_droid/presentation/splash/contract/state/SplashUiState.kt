package com.aloe_droid.presentation.splash.contract.state

import androidx.compose.runtime.Stable
import com.aloe_droid.presentation.base.UiContract

@Stable
data class SplashUiState(val isLoading: Boolean) : UiContract.State
