package com.aloe_droid.presentation.map

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.aloe_droid.presentation.base.BaseViewModel
import com.aloe_droid.presentation.base.UiContract.State
import com.aloe_droid.presentation.map.contract.effect.MapEffect
import com.aloe_droid.presentation.map.contract.event.MapEvent
import com.aloe_droid.presentation.map.contract.route.Map
import com.aloe_droid.presentation.map.contract.state.MapUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MapUiState, MapEvent, MapEffect>(savedStateHandle) {

    override fun initialState(savedStateHandle: SavedStateHandle): State<MapUiState> {
        val map: Map = savedStateHandle.toRoute()
        return State.Idle
    }

    override fun handleEvent(event: MapEvent) = when {
        else -> {}
    }
}
