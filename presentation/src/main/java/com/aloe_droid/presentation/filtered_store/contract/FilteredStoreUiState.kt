package com.aloe_droid.presentation.filtered_store.contract

import androidx.compose.runtime.Stable
import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.filtered_store.data.StoreFilter

@Stable
data class FilteredStoreUiState(
    val isRefresh: Boolean = false,
    val isShowOrderBottomSheet: Boolean = false,
    val isShowDistanceBottomSheet: Boolean = false,
    val storeFilter: StoreFilter,
) : UiContract.State
