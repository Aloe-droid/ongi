package com.aloe_droid.presentation.home.contract

import androidx.compose.runtime.Stable
import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.home.data.BannerData
import com.aloe_droid.presentation.home.data.CategoryData
import com.aloe_droid.presentation.home.data.LocationData
import com.aloe_droid.presentation.home.data.StoreData
import com.google.android.gms.common.api.ResolvableApiException

@Stable
data class HomeUiState(
    val isInitialState: Boolean = true,
    val isRefreshing: Boolean = false,
    val isNeedPermission: Boolean = false,
    val categoryList: List<CategoryData> = CategoryData.Companion.CategoryList,
    val bannerList: List<BannerData> = emptyList(),
    val favoriteStoreList: List<StoreData> = emptyList(),
    val nearbyStoreList: List<StoreData> = emptyList(),
    val locationData: LocationData = LocationData(),
    val gpsError: ResolvableApiException? = null
) : UiContract.State
