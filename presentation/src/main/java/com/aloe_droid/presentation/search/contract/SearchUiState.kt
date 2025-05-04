package com.aloe_droid.presentation.search.contract

import androidx.compose.runtime.Stable
import com.aloe_droid.presentation.base.view.UiContract

@Stable
data class SearchUiState(
    val isInitialState: Boolean = true,
    val searchQuery: String = "",
) : UiContract.State
