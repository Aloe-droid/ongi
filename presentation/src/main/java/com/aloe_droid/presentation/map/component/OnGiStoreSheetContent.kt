package com.aloe_droid.presentation.map.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aloe_droid.presentation.map.data.StoreMapData

@Composable
fun OnGiStoreSheetContent(
    modifier: Modifier = Modifier,
    selectedStore: StoreMapData? = null,
    state: LazyListState,
    storeItems: List<StoreMapData>,
    selectStore: (StoreMapData) -> Unit,
) {
    if (selectedStore == null) {
        SheetStoreContents(
            modifier = modifier,
            state = state,
            storeItems = storeItems,
            selectStore = selectStore
        )
    } else {
        SelectedStoreContent(
            modifier = modifier.fillMaxSize(),
            selectedStore = selectedStore,
        )
    }
}
