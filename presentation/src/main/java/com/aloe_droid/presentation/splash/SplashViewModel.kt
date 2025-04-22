package com.aloe_droid.presentation.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aloe_droid.domain.usecase.FindOrCreateUserUseCase
import com.aloe_droid.presentation.base.BaseViewModel
import com.aloe_droid.presentation.splash.contract.effect.SplashEffect
import com.aloe_droid.presentation.splash.contract.event.SplashEvent
import com.aloe_droid.presentation.splash.contract.state.SplashUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val findOrCreateUserUseCase: FindOrCreateUserUseCase
) : BaseViewModel<SplashUiState, SplashEvent, SplashEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): SplashUiState {
        sendEvent(SplashEvent.CheckAuth)
        return SplashUiState(isLoading = true)
    }

    override fun handleEvent(event: SplashEvent) = when (event) {
        SplashEvent.CheckAuth -> checkAuth()
    }

    override fun handleError(throwable: Throwable) {
        super.handleError(throwable)
        sendSideEffect(SplashEffect.FinishSplashScreen(throwable))
    }

    private fun checkAuth() {
        viewModelScope.launch {
            findOrCreateUserUseCase().safeCollect {
                updateState { splashUiState: SplashUiState ->
                    splashUiState.copy(isLoading = false)
                }
            }
        }
    }
}
