package com.aloe_droid.presentation.map

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.LoadingScreen
import com.aloe_droid.presentation.base.view.ScreenTransition
import com.aloe_droid.presentation.map.component.CollectMapSideEffects
import com.aloe_droid.presentation.map.component.LocationHandler
import com.aloe_droid.presentation.map.contract.Map
import com.aloe_droid.presentation.map.contract.MapEvent
import com.aloe_droid.presentation.map.contract.MapUiData
import com.aloe_droid.presentation.map.contract.MapUiState
import com.aloe_droid.presentation.map.data.MapData
import com.aloe_droid.presentation.map.data.StoreMapData
import com.aloe_droid.presentation.setting.contract.Setting
import com.aloe_droid.presentation.store.contract.Store
import com.naver.maps.geometry.LatLng
import java.util.UUID

fun NavGraphBuilder.mapScreen(
    showSnackMessage: (SnackbarVisuals) -> Unit,
    navigateToStore: (UUID) -> Unit,
) = composable<Map>(
    enterTransition = {
        if (initialState.destination.hasRoute<Setting>()) ScreenTransition.slideInFromLeft()
        else if (initialState.destination.hasRoute<Store>()) ScreenTransition.fadeInAnim()
        else ScreenTransition.slideInFromRight()
    },
    exitTransition = {
        if (targetState.destination.hasRoute<Setting>()) ScreenTransition.slideOutToLeft()
        else if (targetState.destination.hasRoute<Store>()) ScreenTransition.fadeOutAnim()
        else ScreenTransition.slideOutToRight()
    },
) {
    val viewModel: MapViewModel = hiltViewModel()
    val uiState: MapUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiData: MapUiData by viewModel.uiData.collectAsStateWithLifecycle()
    val storeListState: LazyListState = rememberLazyListState()

    CollectMapSideEffects(
        sideEffect = viewModel.uiEffect,
        storeListState = storeListState,
        showSnackMessage = showSnackMessage,
        navigateToStore = navigateToStore
    )

    LocationHandler(uiState = uiState, viewModel = viewModel)

    if (uiState.isInitialState) {
        LoadingScreen(content = stringResource(id = R.string.loading))
    } else {
        MapScreen(
            location = LatLng(uiState.locationData.latitude, uiState.locationData.longitude),
            storeListState = storeListState,
            storeItems = uiData.searchedStoreList,
            selectedMarkerStore = uiData.selectedMarkerStore,
            onLocationCheck = {
                val event = MapEvent.CheckLocation
                viewModel.sendEvent(event = event)
            },
            onMarkerClick = { store: StoreMapData ->
                val event = MapEvent.SelectStoreMarker(storeData = store)
                viewModel.sendEvent(event = event)
            },
            selectStore = { store: StoreMapData ->
                val event = MapEvent.SelectStore(storeData = store)
                viewModel.sendEvent(event = event)
            },
            onChangeMapData = { map: MapData ->
                val event = MapEvent.ChangeMapInfo(mapData = map)
                viewModel.sendEvent(event = event)
            },
            onSearch = {
                val event = MapEvent.SearchNearbyStores
                viewModel.sendEvent(event = event)
            }
        )
    }
}
