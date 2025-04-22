package com.aloe_droid.presentation.home

import androidx.lifecycle.SavedStateHandle
import com.aloe_droid.presentation.base.BaseViewModel
import com.aloe_droid.presentation.home.contract.effect.HomeEffect
import com.aloe_droid.presentation.home.contract.event.HomeEvent
import com.aloe_droid.presentation.home.contract.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeUiState, HomeEvent, HomeEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): HomeUiState {
        return HomeUiState
    }

    override fun handleEvent(event: HomeEvent) = when {
        else -> {}
    }

}
