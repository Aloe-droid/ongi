package com.aloe_droid.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.aloe_droid.domain.entity.SearchHistory
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.SemiLargePadding
import com.aloe_droid.presentation.base.theme.SmallPadding
import com.aloe_droid.presentation.search.component.Histories
import com.aloe_droid.presentation.search.component.OnGiSearchBar
import com.aloe_droid.presentation.search.data.SearchedStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    canGoUp: Boolean,
    storeItems: LazyPagingItems<SearchedStore>,
    historyItems: LazyPagingItems<SearchHistory>,
    query: String,
    navigateUp: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    selectStore: (SearchedStore) -> Unit,
    deleteHistory: (Long) -> Unit,
    deleteAllHistory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SemiLargePadding)
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        OnGiSearchBar(
            canGoUp = canGoUp,
            navigateUp = navigateUp,
            query = query,
            onQueryChange = onQueryChange,
            storeItems = storeItems,
            selectStore = selectStore,
            onSearch = onSearch
        )

        Spacer(modifier = Modifier.height(DefaultPadding))

        Histories(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SmallPadding),
            historyItems = historyItems,
            deleteHistory = deleteHistory,
            onSearch = onSearch,
            deleteAllHistory = deleteAllHistory
        )
    }
}
