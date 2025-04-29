package com.aloe_droid.presentation.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aloe_droid.presentation.base.theme.LargePadding
import com.aloe_droid.presentation.home.component.BannerPager
import com.aloe_droid.presentation.home.component.CategoryGrid
import com.aloe_droid.presentation.home.component.FavoriteStoreListView
import com.aloe_droid.presentation.home.component.NearbyStoreListView
import com.aloe_droid.presentation.home.data.BannerData
import com.aloe_droid.presentation.home.data.CategoryData
import com.aloe_droid.presentation.home.data.CategoryData.Companion.CategoryList
import com.aloe_droid.presentation.home.data.StoreData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    bannerList: List<BannerData>,
    categoryList: List<CategoryData>,
    favoriteStoreList: List<StoreData>,
    nearbyStoreList: List<StoreData>,
    onRefresh: () -> Unit,
    selectBanner: (BannerData) -> Unit,
    selectCategory: (CategoryData) -> Unit,
    selectStore: (StoreData) -> Unit,
    selectFavoriteStoreList: () -> Unit,
    selectNearbyStoreList: () -> Unit
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
    ) {
        val scrollState: ScrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .padding(bottom = LargePadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = LargePadding)
        ) {

            BannerPager(
                bannerList = bannerList,
                selectBanner = selectBanner
            )

            CategoryGrid(
                categoryList = categoryList,
                selectCategory = selectCategory
            )

            FavoriteStoreListView(
                favoriteStoreList = favoriteStoreList,
                selectStore = selectStore,
                clickFavorite = selectFavoriteStoreList
            )

            NearbyStoreListView(
                nearbyStoreList = nearbyStoreList,
                selectStore = selectStore,
                clickNearby = selectNearbyStoreList
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        isRefreshing = false,
        categoryList = CategoryList,
        bannerList = emptyList(),
        favoriteStoreList = emptyList(),
        nearbyStoreList = emptyList(),
        onRefresh = {},
        selectCategory = {},
        selectBanner = {},
        selectFavoriteStoreList = {},
        selectNearbyStoreList = {},
        selectStore = {}
    )
}