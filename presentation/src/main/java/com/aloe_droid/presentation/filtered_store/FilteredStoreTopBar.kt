package com.aloe_droid.presentation.filtered_store

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.aloe_droid.presentation.base.theme.DefaultTopBarMaxHeight
import com.aloe_droid.presentation.filtered_store.contract.FilteredStoreUiState

@Composable
fun FilteredStoreTopBar(
    backStackEntry: NavBackStackEntry,
    navigateUp: () -> Unit,
    navigateToSearch: () -> Unit,
) {
    val viewModel: FilteredStoreViewModel = hiltViewModel(viewModelStoreOwner = backStackEntry)
    val uiState: FilteredStoreUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val title = with(uiState.storeFilter) {
        if (searchQuery.isNotBlank()) searchQuery
        else stringResource(id = category.getNameRes())
    }

    FilteredStoreTopBar(
        title = title,
        navigateUp = navigateUp,
        navigateToSearch = navigateToSearch
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun FilteredStoreTopBar(
    title: String,
    navigateUp: () -> Unit,
    navigateToSearch: () -> Unit
) {
    TopAppBar(
        expandedHeight = DefaultTopBarMaxHeight,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로가기"
                )
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            IconButton(onClick = navigateToSearch) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "검색"
                )
            }
        }
    )
}

@Composable
@Preview
fun FilteredStoreTopBarPreview() {
    FilteredStoreTopBar(
        title = "일식",
        navigateUp = {},
        navigateToSearch = {}
    )
}