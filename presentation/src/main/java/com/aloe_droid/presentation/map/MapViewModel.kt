package com.aloe_droid.presentation.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aloe_droid.domain.entity.Location
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.entity.StoreMapEntity
import com.aloe_droid.domain.exception.LocationPermissionException
import com.aloe_droid.domain.usecase.GetLocationUseCase
import com.aloe_droid.domain.usecase.GetMapInfoUseCase
import com.aloe_droid.domain.usecase.GetStoreInfoUseCase
import com.aloe_droid.presentation.base.view.BaseViewModel
import com.aloe_droid.presentation.home.data.LocationData.Companion.toLocationData
import com.aloe_droid.presentation.map.contract.Map
import com.aloe_droid.presentation.map.contract.MapEffect
import com.aloe_droid.presentation.map.contract.MapEvent
import com.aloe_droid.presentation.map.contract.MapUiState
import com.aloe_droid.presentation.map.data.MapData
import com.aloe_droid.presentation.map.data.StoreMapData
import com.aloe_droid.presentation.map.data.StoreMapData.Companion.toStoreMapData
import com.aloe_droid.presentation.map.data.StoreMapData.Companion.toStoreMapDataList
import com.google.android.gms.common.api.ResolvableApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMapInfoUseCase: GetMapInfoUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getStoreInfoUseCase: GetStoreInfoUseCase,
) : BaseViewModel<MapUiState, MapEvent, MapEffect>(savedStateHandle) {

    private var selectedStoreJob: Job? = null

    override fun initState(savedStateHandle: SavedStateHandle): MapUiState {
        return MapUiState()
    }

    override fun handleEvent(event: MapEvent) {
        when (event) {
            MapEvent.LoadEvent -> handleLoad()
            MapEvent.LocationRetry -> handleRetry()
            is MapEvent.LocationSkip -> handlePermissionSkip(event.skipMessage)
            MapEvent.CheckLocation -> handleCheckLocation()
            is MapEvent.ChangeMapInfo -> handleChangeMapInfo(event.mapData)
            is MapEvent.SelectStoreMarker -> handleSelectStoreMaker(event.storeData)
            MapEvent.SearchNearbyStores -> handleSearchStores()
            is MapEvent.SelectStore -> handleSelectStore(event.storeData)
        }
    }

    private fun handleLoad() = viewModelScope.safeLaunch {
        val route = Map::class.java.name
        val storeEntity: StoreMapEntity = getMapInfoUseCase.invoke(route = route).first()
        updateState { uiState: MapUiState ->
            uiState.copy(
                isInitialState = false,
                selectedMarkerStore = null,
                locationData = storeEntity.location.toLocationData(),
                searchedStoreList = storeEntity.stores.toStoreMapDataList(),
                mapData = MapData(
                    mapCenter = storeEntity.location.toLocationData(),
                    distance = uiState.mapData.distance
                )
            )
        }
    }

    private fun handleChangeMapInfo(mapData: MapData) {
        updateState { uiState: MapUiState ->
            uiState.copy(mapData = mapData)
        }
    }

    private fun handleSearchStores() = viewModelScope.safeLaunch {
        selectedStoreJob?.cancel()
        updateState { uiState: MapUiState ->
            uiState.copy(selectedMarkerStore = null)
        }

        val effect = MapEffect.ScrollToFirstPosition
        sendSideEffect(uiEffect = effect)

        val mapData = currentState.mapData
        val route = Map::class.java.name
        getMapInfoUseCase(
            route = route,
            distance = mapData.distance,
            latitude = mapData.mapCenter.latitude,
            longitude = mapData.mapCenter.longitude
        ).safeCollect { storeList: List<Store> ->
            updateState { uiState: MapUiState ->
                uiState.copy(
                    searchedStoreList = storeList.toStoreMapDataList(
                        myLat = currentState.locationData.latitude,
                        myLon = currentState.locationData.longitude
                    )
                )
            }
        }
    }

    private fun handleSelectStoreMaker(storeData: StoreMapData) {
        viewModelScope.safeLaunch {
            getStoreInfoUseCase.getLocalStore(storeId = storeData.id).safeCollect { store: Store ->
                updateState { uiState: MapUiState ->
                    uiState.copy(selectedMarkerStore = store.toStoreMapData())
                }
            }
        }.also {
            selectedStoreJob = it
        }
    }

    private fun handleSelectStore(storeData: StoreMapData) {
        val effect: MapEffect = MapEffect.NavigateStore(id = storeData.id)
        sendSideEffect(uiEffect = effect)
    }

    private fun handleCheckLocation() = viewModelScope.safeLaunch {
        getLocationUseCase().safeCollect { location: Location ->
            if (location.isDefault) handleLocationError(location.error)

            updateState { uiState: MapUiState ->
                uiState.copy(
                    isInitialState = false,
                    locationData = location.toLocationData()
                )
            }
        }
    }

    private fun handleLocationError(throwable: Throwable?) = when (throwable) {
        is ResolvableApiException -> handleNeedGPS(throwable = throwable)
        is LocationPermissionException -> handleNeedPermission(throwable = throwable)
        else -> Timber.e(throwable)
    }

    private fun handleNeedPermission(throwable: Throwable) {
        Timber.e(throwable)

        updateState { state: MapUiState ->
            state.copy(isNeedPermission = true)
        }
    }

    private fun handleNeedGPS(throwable: ResolvableApiException) {
        updateState { state: MapUiState ->
            state.copy(gpsError = throwable)
        }
    }

    private fun handleRetry() {
        updateState { state: MapUiState ->
            state.copy(isNeedPermission = false, gpsError = null, isInitialState = true)
        }
    }

    private fun handlePermissionSkip(skipMessage: String) {
        showErrorMessage(skipMessage)

        updateState { state: MapUiState ->
            state.copy(isNeedPermission = false, gpsError = null)
        }
    }

    override fun handleError(throwable: Throwable) {
        super.handleError(throwable)
        updateState { state: MapUiState ->
            state.copy(isInitialState = false)
        }

        throwable.message?.let { message: String ->
            showErrorMessage(message = message)
        }
    }

    private fun showErrorMessage(message: String) {
        val effect: MapEffect = MapEffect.ShowErrorMessage(message = message)
        sendSideEffect(uiEffect = effect)
    }
}
