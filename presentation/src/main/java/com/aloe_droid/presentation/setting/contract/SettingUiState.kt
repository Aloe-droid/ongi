package com.aloe_droid.presentation.setting.contract

import androidx.compose.runtime.Stable
import com.aloe_droid.presentation.base.view.UiContract
import kotlinx.datetime.Instant

@Stable
data class SettingUiState(
    val isInitialState: Boolean = true,
    val storeCount: Long = 0L,
    val syncTime: Instant = Instant.fromEpochMilliseconds(0L)
) : UiContract.State
