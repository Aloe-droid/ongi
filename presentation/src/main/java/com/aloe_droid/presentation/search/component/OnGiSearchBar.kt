package com.aloe_droid.presentation.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.ExtraLargeImageSize
import com.aloe_droid.presentation.base.theme.SemiLargePadding
import com.aloe_droid.presentation.base.theme.SmallPadding
import com.aloe_droid.presentation.search.data.SearchedStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnGiSearchBar(
    modifier: Modifier = Modifier,
    canGoUp: Boolean,
    storeItems: LazyPagingItems<SearchedStore>,
    query: String,
    navigateUp: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    selectStore: (SearchedStore) -> Unit
) {
    val (expanded, setExpanded) = rememberSaveable { mutableStateOf(false) }

    SearchBar(
        modifier = modifier,
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        inputField = {
            SearchField(
                modifier = Modifier.fillMaxWidth(),
                query = query,
                onQueryChange = onQueryChange,
                expanded = expanded,
                setExpanded = setExpanded,
                navigateUp = {
                    if (canGoUp) navigateUp()
                    else setExpanded(false)
                },
                onSearch = {
                    onSearch(query)
                    setExpanded(false)
                }
            )
        },
        windowInsets = WindowInsets(0),
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
        expanded = expanded,
        onExpandedChange = setExpanded,
    ) {

        if (storeItems.loadState.isIdle && storeItems.itemCount == 0 && query.isNotBlank()) {
            NoSearchItem()
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(SmallPadding)
        ) {
            items(count = storeItems.itemCount) { index: Int ->
                val item: SearchedStore = storeItems[index] ?: return@items
                SearchedStoreScreen(
                    modifier = Modifier.fillMaxWidth(),
                    name = item.name,
                    category = item.category,
                    distance = item.distance,
                    onClick = { selectStore(item) },
                )
            }
        }
    }
}

@Composable
private fun NoSearchItem() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = DefaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier.size(ExtraLargeImageSize),
            painter = painterResource(id = R.drawable.no_store),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(SemiLargePadding))

        Text(
            text = stringResource(id = R.string.no_search_result),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(2f))
    }
}
