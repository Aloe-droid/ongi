package com.aloe_droid.presentation.search

import androidx.lifecycle.SavedStateHandle
import com.aloe_droid.presentation.base.view.BaseViewModel
import com.aloe_droid.presentation.search.contract.effect.SearchEffect
import com.aloe_droid.presentation.search.contract.event.SearchEvent
import com.aloe_droid.presentation.search.contract.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SearchUiState, SearchEvent, SearchEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): SearchUiState {
        return SearchUiState
    }

    override fun handleEvent(event: SearchEvent) = when {
        else -> {}
    }

}
