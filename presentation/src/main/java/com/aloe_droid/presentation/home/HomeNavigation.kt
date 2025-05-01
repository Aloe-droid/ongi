package com.aloe_droid.presentation.home

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.GpsHandler
import com.aloe_droid.presentation.base.component.LoadingScreen
import com.aloe_droid.presentation.base.component.PermissionHandler
import com.aloe_droid.presentation.base.view.BaseSnackBarVisuals
import com.aloe_droid.presentation.base.view.CollectSideEffects
import com.aloe_droid.presentation.filtered_store.data.StoreFilter
import com.aloe_droid.presentation.home.contract.Home
import com.aloe_droid.presentation.home.contract.HomeEffect
import com.aloe_droid.presentation.home.contract.HomeEvent
import com.aloe_droid.presentation.home.data.BannerData
import com.aloe_droid.presentation.home.data.CategoryData
import com.aloe_droid.presentation.home.data.StoreData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.common.api.ResolvableApiException

@OptIn(ExperimentalPermissionsApi::class)
fun NavGraphBuilder.homeScreen(
    showSnackMessage: (SnackbarVisuals) -> Unit,
    navigateToFilteredStore: (StoreFilter) -> Unit,
) = composable<Home> {

    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val context: Context = LocalContext.current

    CollectSideEffects(homeViewModel.uiEffect) { sideEffect: HomeEffect ->
        when (sideEffect) {
            is HomeEffect.ShowErrorMessage -> {
                val snackBarVisuals = BaseSnackBarVisuals(message = sideEffect.message)
                showSnackMessage(snackBarVisuals)
            }

            is HomeEffect.ShowBrowser -> {
                CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .build()
                    .launchUrl(context, sideEffect.url.toUri())
            }

            is HomeEffect.NavigateStore -> {
                showSnackMessage(BaseSnackBarVisuals("넘어갑니다. ${sideEffect.id}"))
            }

            is HomeEffect.NavigateStoreList -> {
                val filter: StoreFilter = sideEffect.filter
                navigateToFilteredStore(filter)
            }
        }
    }

    if (uiState.isNeedPermission) {
        PermissionHandler(
            permissions = listOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION),
            onRetry = {
                val event: HomeEvent = HomeEvent.LocationRetry
                homeViewModel.sendEvent(event = event)
            },
            onSkip = {
                val denyMessage: String = context.getString(R.string.deny_location)
                val event: HomeEvent = HomeEvent.LocationSkip(skipMessage = denyMessage)
                homeViewModel.sendEvent(event = event)
            }
        )
    }

    uiState.gpsError?.let { error: ResolvableApiException ->
        GpsHandler(
            gpsError = error,
            onEnabled = {
                val event: HomeEvent = HomeEvent.LocationRetry
                homeViewModel.sendEvent(event = event)
            },
            onDisabled = {
                val denyMessage: String = context.getString(R.string.deny_gps)
                val event: HomeEvent = HomeEvent.LocationSkip(skipMessage = denyMessage)
                homeViewModel.sendEvent(event = event)
            }
        )
    }

    if (uiState.isInitialState) {
        homeViewModel.sendEvent(event = HomeEvent.LoadEvent)
        LoadingScreen(content = stringResource(id = R.string.loading))
    } else {
        HomeScreen(
            isRefreshing = uiState.isRefreshing,
            bannerList = uiState.bannerList,
            categoryList = uiState.categoryList,
            favoriteStoreList = uiState.favoriteStoreList,
            nearbyStoreList = uiState.nearbyStoreList,
            selectBanner = { banner: BannerData ->
                val event: HomeEvent = HomeEvent.SelectBannerEvent(bannerData = banner)
                homeViewModel.sendEvent(event = event)
            },
            selectCategory = { category: CategoryData ->
                val event: HomeEvent = HomeEvent.SelectCategoryEvent(categoryData = category)
                homeViewModel.sendEvent(event = event)
            },
            selectFavoriteStoreList = {
                val event = HomeEvent.SelectFavoriteStoreListEvent
                homeViewModel.sendEvent(event = event)
            },
            selectNearbyStoreList = {
                val event: HomeEvent = HomeEvent.SelectNearbyStoreListEvent
                homeViewModel.sendEvent(event = event)
            },
            onRefresh = {
                val event: HomeEvent = HomeEvent.RefreshEvent
                homeViewModel.sendEvent(event = event)
            },
            selectStore = { storeData: StoreData ->
                val event: HomeEvent = HomeEvent.SelectStore(storeData = storeData)
                homeViewModel.sendEvent(event = event)
            }
        )
    }
}
