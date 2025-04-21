package com.aloe_droid.presentation.setting

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.aloe_droid.presentation.base.BaseViewModel
import com.aloe_droid.presentation.base.UiContract.State
import com.aloe_droid.presentation.setting.contract.effect.SettingEffect
import com.aloe_droid.presentation.setting.contract.event.SettingEvent
import com.aloe_droid.presentation.setting.contract.route.Setting
import com.aloe_droid.presentation.setting.contract.state.SettingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SettingUiState, SettingEvent, SettingEffect>(savedStateHandle) {

    override fun initialState(savedStateHandle: SavedStateHandle): State<SettingUiState> {
        val setting: Setting = savedStateHandle.toRoute()
        return State.Idle
    }

    override fun handleEvent(event: SettingEvent) = when {
        else -> {}
    }
}
