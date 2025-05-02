package com.aloe_droid.ongi.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.aloe_droid.ongi.ui.navigation.NavUtil.Companion.safeMove
import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.filtered_store.contract.FilteredStore
import com.aloe_droid.presentation.filtered_store.data.StoreFilter
import com.aloe_droid.presentation.filtered_store.filteredStoreScreen
import com.aloe_droid.presentation.home.homeScreen
import com.aloe_droid.presentation.map.contract.route.mapScreen
import com.aloe_droid.presentation.search.contract.route.searchScreen
import com.aloe_droid.presentation.setting.contract.route.settingScreen
import com.aloe_droid.presentation.store.contract.Store
import com.aloe_droid.presentation.store.storeScreen
import java.util.UUID

@Composable
fun OnGiNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startRoute: UiContract.Route,
    showSnackMessage: (SnackbarVisuals) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startRoute,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        homeScreen(
            showSnackMessage = showSnackMessage,
            navigateToFilteredStore = { filter: StoreFilter ->
                navController.safeMove {
                    val filteredStore = FilteredStore(storeFilter = filter)
                    navigate(route = filteredStore)
                }
            },
            navigateToStore = { id: UUID ->
                navController.safeMove {
                    val store = Store(id = id.toString())
                    navigate(route = store)
                }
            }
        )

        searchScreen()

        mapScreen()

        settingScreen()

        filteredStoreScreen(
            showSnackMessage = showSnackMessage,
            navigateToStore = { id: UUID ->
                navController.safeMove {
                    val store = Store(id = id.toString())
                    navigate(route = store)
                }
            }
        )

        storeScreen(showSnackMessage = showSnackMessage) {
            navController.safeMove {
                navigateUp()
            }
        }
    }
}
