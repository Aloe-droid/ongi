package com.aloe_droid.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.aloe_droid.domain.entity.SearchHistory
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.usecase.DeleteSearchHistoryUseCase
import com.aloe_droid.domain.usecase.GetFilteredStoreUseCase
import com.aloe_droid.domain.usecase.GetSearchHistoryUseCase
import com.aloe_droid.domain.usecase.InsertSearchHistoryUseCase
import com.aloe_droid.presentation.base.view.BaseViewModel
import com.aloe_droid.presentation.search.contract.SearchEffect
import com.aloe_droid.presentation.search.contract.SearchEvent
import com.aloe_droid.presentation.search.contract.SearchUiState
import com.aloe_droid.presentation.search.data.SearchedStore
import com.aloe_droid.presentation.search.data.SearchedStore.Companion.toSearchedStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getFilteredStoreUseCase: GetFilteredStoreUseCase,
    getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase
) : BaseViewModel<SearchUiState, SearchEvent, SearchEffect>(savedStateHandle) {

    val queryResult: StateFlow<PagingData<SearchedStore>> = uiState.debounce { DEFAULT_DEBOUNCE }
        .map { it.searchQuery }
        .filter { it.isNotBlank() }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getFilteredStoreUseCase(searchQuery = query)
        }.map { pagingData: PagingData<Store> ->
            pagingData.map { store: Store ->
                store.toSearchedStore()
            }
        }.cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_OUT),
            initialValue = PagingData.empty<SearchedStore>()
        )

    val searchHistory: StateFlow<PagingData<SearchHistory>> = uiState
        .distinctUntilChangedBy { it.isInitialState }
        .flatMapLatest { getSearchHistoryUseCase() }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_OUT),
            initialValue = PagingData.empty<SearchHistory>()
        )

    override fun initState(savedStateHandle: SavedStateHandle): SearchUiState {
        return SearchUiState()
    }

    override fun handleEvent(event: SearchEvent) = when (event) {
        SearchEvent.LoadEvent -> handleLoad()
        SearchEvent.NavigateUpEvent -> handleNavigateUp()
        SearchEvent.DeleteAllQuery -> handleDeleteAllHistory()
        is SearchEvent.ChangeQuery -> handleChangeQuery(event.query)
        is SearchEvent.SearchQuery -> handleSearchQuery(event.query)
        is SearchEvent.DeleteQuery -> handleDeleteHistory(event.id)
        is SearchEvent.SelectStore -> handleSelectStore(event.id)
    }

    override fun handleError(throwable: Throwable) {
        super.handleError(throwable)
        updateState { state: SearchUiState ->
            state.copy(isInitialState = false)
        }

        throwable.message?.let { message: String ->
            showErrorMessage(message = message)
        }
    }

    private fun handleLoad() = viewModelScope.safeLaunch {
        updateState { uiState: SearchUiState ->
            uiState.copy(isInitialState = false)
        }
    }

    private fun handleNavigateUp() {
        val effect: SearchEffect = SearchEffect.NavigateUp
        sendSideEffect(uiEffect = effect)
    }

    private fun handleDeleteAllHistory() = viewModelScope.safeLaunch {
        deleteSearchHistoryUseCase()
    }

    private fun handleChangeQuery(query: String) {
        updateState { uiState: SearchUiState ->
            uiState.copy(searchQuery = query)
        }
    }

    private fun handleSearchQuery(query: String) = viewModelScope.safeLaunch {
        insertSearchHistoryUseCase(keyword = query)
        updateState { uiState: SearchUiState ->
            uiState.copy(searchQuery = query)
        }

        val effect: SearchEffect = SearchEffect.NavigateToFilteredStoreList(query = query)
        sendSideEffect(uiEffect = effect)
    }

    private fun handleDeleteHistory(historyId: Long) = viewModelScope.safeLaunch {
        deleteSearchHistoryUseCase(historyId = historyId)
    }

    private fun handleSelectStore(storeId: UUID) {
        val effect: SearchEffect = SearchEffect.SelectStore(storeId = storeId)
        sendSideEffect(uiEffect = effect)
    }

    private fun showErrorMessage(message: String) {
        val effect: SearchEffect = SearchEffect.ShowErrorMessage(message = message)
        sendSideEffect(uiEffect = effect)
    }

}
