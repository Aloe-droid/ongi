package com.aloe_droid.presentation.setting

import androidx.lifecycle.SavedStateHandle
import com.aloe_droid.presentation.base.view.BaseViewModel
import com.aloe_droid.presentation.setting.contract.effect.SettingEffect
import com.aloe_droid.presentation.setting.contract.event.SettingEvent
import com.aloe_droid.presentation.setting.contract.state.SettingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SettingUiState, SettingEvent, SettingEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): SettingUiState {
        return SettingUiState
    }

    override fun handleEvent(event: SettingEvent) = when {
        else -> {}
    }
}
