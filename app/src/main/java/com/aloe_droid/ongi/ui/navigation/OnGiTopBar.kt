package com.aloe_droid.ongi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import com.aloe_droid.presentation.filtered_store.FilteredStoreTopBar
import com.aloe_droid.presentation.filtered_store.contract.FilteredStore
import com.aloe_droid.presentation.store.StoreTopBar
import com.aloe_droid.presentation.store.contract.Store

@Composable
fun OnGiTopBar(
    backStackEntry: NavBackStackEntry,
    navigateUp: () -> Unit,
    navigateToSearch: () -> Unit,
) {
    if (backStackEntry.destination.hasRoute(FilteredStore::class) == true) {
        FilteredStoreTopBar(
            backStackEntry = backStackEntry,
            navigateUp = navigateUp,
            navigateToSearch = navigateToSearch
        )
    } else if (backStackEntry.destination.hasRoute(Store::class) == true) {
        StoreTopBar(
            backStackEntry = backStackEntry,
            navigateUp = navigateUp,
        )
    }
}
