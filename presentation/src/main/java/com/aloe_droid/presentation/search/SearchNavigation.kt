package com.aloe_droid.presentation.search

import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aloe_droid.domain.entity.SearchHistory
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.LoadingScreen
import com.aloe_droid.presentation.base.view.BaseSnackBarVisuals
import com.aloe_droid.presentation.base.view.CollectSideEffects
import com.aloe_droid.presentation.base.view.ScreenTransition
import com.aloe_droid.presentation.filtered_store.contract.FilteredStore
import com.aloe_droid.presentation.home.contract.Home
import com.aloe_droid.presentation.search.contract.Search
import com.aloe_droid.presentation.search.contract.SearchEffect
import com.aloe_droid.presentation.search.contract.SearchEvent
import com.aloe_droid.presentation.search.contract.SearchUiState
import com.aloe_droid.presentation.search.data.SearchedStore
import java.util.UUID

fun NavGraphBuilder.searchScreen(
    showSnackMessage: (SnackbarVisuals) -> Unit,
    navigateToStore: (UUID) -> Unit,
    navigateToFilteredStore: (String) -> Unit,
    navigateUp: () -> Unit,
) = composable<Search>(
    enterTransition = {
        if (initialState.destination.hasRoute<Home>()) ScreenTransition.slideInFromRight()
        else if (initialState.destination.hasRoute<FilteredStore>()) ScreenTransition.fadeInAnim()
        else ScreenTransition.slideInFromLeft()
    },
    exitTransition = {
        if (targetState.destination.hasRoute<Home>()) ScreenTransition.slideOutToRight()
        else if (targetState.destination.hasRoute<FilteredStore>()) ScreenTransition.fadeOutAnim()
        else ScreenTransition.slideOutToLeft()
    },
    popEnterTransition = {
        ScreenTransition.slideInFromLeft()
    }
) { backStackEntry: NavBackStackEntry ->
    val fromBottomNavi: Boolean? = backStackEntry.arguments?.getBoolean(Search.iS_BOTTOM)
    val viewModel: SearchViewModel = hiltViewModel()
    val uiState: SearchUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val storeItems: LazyPagingItems<SearchedStore> = viewModel.queryResult
        .collectAsLazyPagingItems()
    val historyItems: LazyPagingItems<SearchHistory> = viewModel.searchHistory
        .collectAsLazyPagingItems()

    CollectSideEffects(effectFlow = viewModel.uiEffect) { sideEffect: SearchEffect ->
        when (sideEffect) {
            is SearchEffect.ShowErrorMessage -> {
                val snackBarVisuals = BaseSnackBarVisuals(message = sideEffect.message)
                showSnackMessage(snackBarVisuals)
            }

            is SearchEffect.SelectStore -> {
                navigateToStore(sideEffect.storeId)
            }

            is SearchEffect.NavigateToFilteredStoreList -> {
                navigateToFilteredStore(sideEffect.query)
            }

            is SearchEffect.NavigateUp -> {
                navigateUp()
            }
        }
    }

    if (uiState.isInitialState) {
        viewModel.sendEvent(event = SearchEvent.LoadEvent)
        LoadingScreen(content = stringResource(id = R.string.loading))
    } else {
        SearchScreen(
            canGoUp = fromBottomNavi == false,
            storeItems = storeItems,
            historyItems = historyItems,
            query = uiState.searchQuery,
            navigateUp = {
                val event = SearchEvent.NavigateUpEvent
                viewModel.sendEvent(event = event)
            },
            onQueryChange = { query: String ->
                val event = SearchEvent.ChangeQuery(query = query)
                viewModel.sendEvent(event = event)
            },
            onSearch = { query: String ->
                if (query.isBlank()) return@SearchScreen
                val event = SearchEvent.SearchQuery(query = query)
                viewModel.sendEvent(event = event)
            },
            selectStore = { storeId: UUID ->
                val event = SearchEvent.SelectStore(id = storeId)
                viewModel.sendEvent(event = event)
            },
            deleteHistory = { historyId: Long ->
                val event = SearchEvent.DeleteQuery(id = historyId)
                viewModel.sendEvent(event = event)
            },
            deleteAllHistory = {
                val event = SearchEvent.DeleteAllQuery
                viewModel.sendEvent(event = event)
            }
        )
    }
}
