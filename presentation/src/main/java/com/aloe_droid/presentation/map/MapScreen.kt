package com.aloe_droid.presentation.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.aloe_droid.presentation.base.theme.MaxBottomSheetHeight
import com.aloe_droid.presentation.map.component.OnGiNaverMap
import com.aloe_droid.presentation.map.component.OnGiStoreSheetContent
import com.aloe_droid.presentation.map.data.MapData
import com.aloe_droid.presentation.map.data.StoreMapData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    location: LatLng,
    storeListState: LazyListState,
    storeItems: List<StoreMapData>,
    selectedMarkerStore: StoreMapData?,
    onLocationCheck: () -> Unit,
    onChangeMapData: (MapData) -> Unit,
    onMarkerClick: (StoreMapData) -> Unit,
    onSearch: () -> Unit,
    selectStore: (StoreMapData) -> Unit
) {
    val sheetState = rememberBottomSheetScaffoldState()
    val peekHeight: Dp = if (selectedMarkerStore != null) {
        MaxBottomSheetHeight
    } else {
        BottomSheetDefaults.SheetPeekHeight
    }

    LaunchedEffect(key1 = selectedMarkerStore) {
        if (selectedMarkerStore != null) {
            sheetState.bottomSheetState.partialExpand()

            snapshotFlow { sheetState.bottomSheetState.currentValue }
                .distinctUntilChanged()
                .collect {
                    if (it == SheetValue.Expanded) {
                        selectStore(selectedMarkerStore)
                        sheetState.bottomSheetState.partialExpand()
                    }
                }
        }
    }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetPeekHeight = peekHeight,
        sheetContent = {
            OnGiStoreSheetContent(
                modifier = Modifier
                    .fillMaxWidth(),
                selectedStore = selectedMarkerStore,
                state = storeListState,
                storeItems = storeItems,
                selectStore = selectStore
            )
        },
        content = {
            OnGiNaverMap(
                location = location,
                storeItems = storeItems,
                onLocationCheck = onLocationCheck,
                onChangeMapData = onChangeMapData,
                onSearch = onSearch,
                onMarkerClick = onMarkerClick
            )
        }
    )
}
