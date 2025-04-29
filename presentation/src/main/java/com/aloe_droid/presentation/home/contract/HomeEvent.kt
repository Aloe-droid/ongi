package com.aloe_droid.presentation.home.contract

import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.home.data.BannerData
import com.aloe_droid.presentation.home.data.CategoryData
import com.aloe_droid.presentation.home.data.StoreData

sealed class HomeEvent : UiContract.Event {
    data object LoadEvent : HomeEvent()
    data object RefreshEvent : HomeEvent()
    data object LocationRetry : HomeEvent()
    data class LocationSkip(val skipMessage: String) : HomeEvent()
    data class SelectCategoryEvent(val categoryData: CategoryData) : HomeEvent()
    data class SelectBannerEvent(val bannerData: BannerData) : HomeEvent()
    data class SelectStore(val storeData: StoreData) : HomeEvent()
    data object SelectFavoriteStoreListEvent : HomeEvent()
    data object SelectNearbyStoreListEvent : HomeEvent()
}
