package com.aloe_droid.presentation.map

import androidx.lifecycle.SavedStateHandle
import com.aloe_droid.presentation.base.BaseViewModel
import com.aloe_droid.presentation.map.contract.effect.MapEffect
import com.aloe_droid.presentation.map.contract.event.MapEvent
import com.aloe_droid.presentation.map.contract.state.MapUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MapUiState, MapEvent, MapEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): MapUiState {
        return MapUiState
    }

    override fun handleEvent(event: MapEvent) = when {
        else -> {}
    }
}
