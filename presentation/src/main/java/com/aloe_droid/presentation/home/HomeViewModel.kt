package com.aloe_droid.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aloe_droid.domain.entity.HomeEntity
import com.aloe_droid.domain.exception.LocationPermissionException
import com.aloe_droid.domain.usecase.GetHomeInfoUseCase
import com.aloe_droid.presentation.base.view.BaseViewModel
import com.aloe_droid.presentation.home.contract.HomeEffect
import com.aloe_droid.presentation.home.contract.HomeEvent
import com.aloe_droid.presentation.home.contract.HomeUiState
import com.aloe_droid.presentation.home.data.BannerData.Companion.toBannerDataList
import com.aloe_droid.presentation.home.data.LocationData.Companion.toLocationData
import com.aloe_droid.presentation.home.data.StoreData
import com.aloe_droid.presentation.home.data.StoreData.Companion.toStoreData
import com.aloe_droid.presentation.filtered_store.data.StoreFilter
import com.aloe_droid.presentation.filtered_store.data.StoreSortType
import com.google.android.gms.common.api.ResolvableApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getHomeInfoUseCase: GetHomeInfoUseCase
) : BaseViewModel<HomeUiState, HomeEvent, HomeEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): HomeUiState = HomeUiState()

    override fun handleEvent(event: HomeEvent) = when (event) {
        is HomeEvent.LoadEvent -> handleLoad()
        is HomeEvent.RefreshEvent -> handleRefresh()
        is HomeEvent.SelectBannerEvent -> handleSelectBanner(event.bannerData.url)
        is HomeEvent.SelectCategoryEvent -> handleSelectStores { copy(category = event.categoryData.storeCategory) }
        is HomeEvent.SelectStore -> handleSelectStore(event.storeData)
        HomeEvent.SelectFavoriteStoreListEvent -> handleSelectStores { copy(sortType = StoreSortType.FAVORITE) }
        HomeEvent.SelectNearbyStoreListEvent -> handleSelectStores { copy(sortType = StoreSortType.DISTANCE) }
        HomeEvent.LocationRetry -> handleRetry()
        is HomeEvent.LocationSkip -> handlePermissionSkip(event.skipMessage)
    }

    override fun handleError(throwable: Throwable) {
        super.handleError(throwable)
        updateState { state: HomeUiState ->
            state.copy(isInitialState = false, isRefreshing = false)
        }

        throwable.message?.let { message: String ->
            showErrorMessage(message = message)
        }
    }

    private fun handleLoad() = viewModelScope.safeLaunch {
        getHomeInfoUseCase().safeCollect { homeEntity: HomeEntity ->
            if (homeEntity.location.isDefault) handleLocationError(homeEntity.location.error)

            updateState { state: HomeUiState ->
                state.copy(
                    isInitialState = false,
                    bannerList = homeEntity.bannerList.toBannerDataList(),
                    locationData = homeEntity.location.toLocationData(),
                    favoriteStoreList = homeEntity.favoriteStoreList.toStoreData(),
                    nearbyStoreList = homeEntity.nearbyStoreList.toStoreData()
                )
            }
        }
    }

    private fun handleRefresh() = viewModelScope.safeLaunch {
        updateState { state: HomeUiState ->
            state.copy(
                isRefreshing = true,
                isNeedPermission = false
            )
        }

        getHomeInfoUseCase().safeCollect { homeEntity: HomeEntity ->
            if (homeEntity.location.isDefault) handleLocationError(homeEntity.location.error)

            updateState { state: HomeUiState ->
                state.copy(
                    isRefreshing = false,
                    bannerList = homeEntity.bannerList.toBannerDataList(),
                    locationData = homeEntity.location.toLocationData(),
                    favoriteStoreList = homeEntity.favoriteStoreList.toStoreData(),
                    nearbyStoreList = homeEntity.nearbyStoreList.toStoreData()
                )
            }
        }
    }

    private fun handleSelectStores(set: StoreFilter.() -> StoreFilter) {
        val storeFilter: StoreFilter = StoreFilter().set()
        val effect: HomeEffect = HomeEffect.NavigateStoreList(filter = storeFilter)
        sendSideEffect(uiEffect = effect)
    }

    private fun handleSelectBanner(url: String) {
        val effect: HomeEffect = HomeEffect.ShowBrowser(url = url)
        sendSideEffect(uiEffect = effect)
    }

    private fun handleSelectStore(storeData: StoreData) {
        val effect: HomeEffect = HomeEffect.NavigateStore(id = storeData.id)
        sendSideEffect(uiEffect = effect)
    }

    private fun handleLocationError(throwable: Throwable?) = when (throwable) {
        is ResolvableApiException -> handleNeedGPS(throwable = throwable)
        is LocationPermissionException -> handleNeedPermission(throwable = throwable)
        else -> Timber.e(throwable)
    }

    private fun handleNeedPermission(throwable: Throwable) {
        Timber.e(throwable)

        updateState { state: HomeUiState ->
            state.copy(isNeedPermission = true, isRefreshing = false)
        }
    }

    private fun handleNeedGPS(throwable: ResolvableApiException) {
        updateState { state: HomeUiState ->
            state.copy(gpsError = throwable, isRefreshing = false)
        }
    }

    private fun handleRetry() {
        updateState { state: HomeUiState ->
            state.copy(isNeedPermission = false, gpsError = null, isInitialState = true)
        }
    }

    private fun handlePermissionSkip(skipMessage: String) {
        showErrorMessage(skipMessage)

        updateState { state: HomeUiState ->
            state.copy(isNeedPermission = false, gpsError = null)
        }
    }

    private fun showErrorMessage(message: String) {
        val effect: HomeEffect = HomeEffect.ShowErrorMessage(message = message)
        sendSideEffect(uiEffect = effect)
    }
}
