package com.aloe_droid.presentation.store.contract

import androidx.compose.runtime.Stable
import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.store.data.StoreData
import java.util.UUID

@Stable
data class StoreUiState(
    val id: UUID,
    val isInitialState: Boolean = true,
    val store: StoreData? = null
) : UiContract.State
