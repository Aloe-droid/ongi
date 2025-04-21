package com.aloe_droid.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.aloe_droid.presentation.base.BaseViewModel
import com.aloe_droid.presentation.base.UiContract.State
import com.aloe_droid.presentation.home.contract.effect.HomeEffect
import com.aloe_droid.presentation.home.contract.event.HomeEvent
import com.aloe_droid.presentation.home.contract.route.Home
import com.aloe_droid.presentation.home.contract.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeUiState, HomeEvent, HomeEffect>(savedStateHandle) {

    override fun initialState(savedStateHandle: SavedStateHandle): State<HomeUiState> {
        val home: Home = savedStateHandle.toRoute()
        return State.Idle
    }

    override fun handleEvent(event: HomeEvent) = when {
        else -> {}
    }

}
