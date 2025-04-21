package com.aloe_droid.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.aloe_droid.presentation.base.BaseViewModel
import com.aloe_droid.presentation.base.UiContract.State
import com.aloe_droid.presentation.search.contract.effect.SearchEffect
import com.aloe_droid.presentation.search.contract.event.SearchEvent
import com.aloe_droid.presentation.search.contract.route.Search
import com.aloe_droid.presentation.search.contract.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SearchUiState, SearchEvent, SearchEffect>(savedStateHandle) {

    override fun initialState(savedStateHandle: SavedStateHandle): State<SearchUiState> {
        val search: Search = savedStateHandle.toRoute()
        return State.Idle
    }

    override fun handleEvent(event: SearchEvent) = when {
        else -> {}
    }

}
