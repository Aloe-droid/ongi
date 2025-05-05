package com.aloe_droid.presentation.filtered_store

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.OnGiRefreshBox
import com.aloe_droid.presentation.base.component.StoreInfo
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.ExtraLargeImageSize
import com.aloe_droid.presentation.base.theme.LargePadding
import com.aloe_droid.presentation.base.theme.SemiLargePadding
import com.aloe_droid.presentation.base.theme.toDistanceString
import com.aloe_droid.presentation.filtered_store.component.FilterBottomSheet
import com.aloe_droid.presentation.filtered_store.component.FilterRow
import com.aloe_droid.presentation.filtered_store.data.StoreDistanceRange
import com.aloe_droid.presentation.filtered_store.data.StoreSortType
import com.aloe_droid.presentation.home.data.StoreData
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteredStoreScreen(
    modifier: Modifier = Modifier,
    isRefresh: Boolean,
    isShowOrderBottomSheet: Boolean,
    isShowDistanceBottomSheet: Boolean,
    isOnlyFavorites: Boolean,
    lazyListState: LazyListState,
    selectedSortType: StoreSortType,
    selectedDistanceRange: StoreDistanceRange,
    storeItems: LazyPagingItems<StoreData>,
    selectStore: (StoreData) -> Unit,
    setShowOrderBottomSheet: () -> Unit,
    setShowDistanceBottomSheet: () -> Unit,
    onSelectSortType: (StoreSortType) -> Unit,
    onSelectDistanceRange: (StoreDistanceRange) -> Unit,
    onDismissBottomSheet: () -> Unit,
    onRefresh: () -> Unit,
) {
    OnGiRefreshBox(
        modifier = modifier.padding(horizontal = LargePadding),
        isRefreshing = isRefresh,
        onRefresh = onRefresh,
    ) {
        Column {
            FilterRow(
                modifier = Modifier
                    .padding(vertical = DefaultPadding)
                    .fillMaxWidth(),
                order = stringResource(id = selectedSortType.getNameRes()),
                setShowOrderBottomSheet = setShowOrderBottomSheet,
                distance = selectedDistanceRange.toDistanceString(),
                setShowDistanceBottomSheet = setShowDistanceBottomSheet
            )

            if (!storeItems.loadState.isIdle || storeItems.itemCount != 0) {
                LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
                    items(storeItems.itemCount) { index ->
                        val data: StoreData = storeItems[index] ?: return@items
                        StoreInfo(
                            modifier = Modifier.fillMaxWidth(),
                            imageUrl = data.imageUrl ?: "",
                            storeName = data.name,
                            storeAddress = "${data.city} ${data.district}",
                            distance = data.distance,
                            favoriteCount = data.favoriteCount,
                            onClick = { selectStore(data) }
                        )
                        Spacer(modifier = Modifier.height(height = LargePadding))
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = DefaultPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        modifier = Modifier.size(ExtraLargeImageSize),
                        painter = painterResource(id = R.drawable.no_store),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(SemiLargePadding))
                    Text(
                        text = stringResource(id = if (isOnlyFavorites) R.string.no_favorite_store else R.string.no_store),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.weight(2f))
                }
            }
        }
    }

    FilterBottomSheet(
        isShowOrderBottomSheet = isShowOrderBottomSheet,
        isShowDistanceBottomSheet = isShowDistanceBottomSheet,
        onDismissBottomSheet = onDismissBottomSheet,
        selectedSortType = selectedSortType,
        onSelectSortType = onSelectSortType,
        selectedDistanceRange = selectedDistanceRange,
        onSelectDistanceRange = onSelectDistanceRange
    )
}

@Composable
@Preview
fun FilteredStoreScreenPreview() {
    val list = List(10) {
        StoreData(
            id = UUID.randomUUID(),
            name = "상호명",
            city = "시도",
            district = "시군구",
            distance = 4.5,
            favoriteCount = 51,
            imageUrl = null
        )
    }
    val fakeFlow = flowOf(PagingData.from(list)).collectAsLazyPagingItems()
    FilteredStoreScreen(
        isRefresh = false,
        isShowOrderBottomSheet = false,
        isShowDistanceBottomSheet = false,
        isOnlyFavorites = false,
        storeItems = fakeFlow,
        selectedSortType = StoreSortType.DISTANCE,
        selectedDistanceRange = StoreDistanceRange.K_10,
        lazyListState = rememberLazyListState(),
        selectStore = {},
        onRefresh = {},
        setShowOrderBottomSheet = {},
        setShowDistanceBottomSheet = {},
        onDismissBottomSheet = {},
        onSelectSortType = {},
        onSelectDistanceRange = {}
    )
}
