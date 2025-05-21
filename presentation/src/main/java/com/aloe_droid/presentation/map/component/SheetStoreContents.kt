package com.aloe_droid.presentation.map.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import com.aloe_droid.presentation.base.component.StoreInfo
import com.aloe_droid.presentation.base.theme.LargePadding
import com.aloe_droid.presentation.base.theme.MaxBottomSheetHeight
import com.aloe_droid.presentation.base.theme.ZeroDp
import com.aloe_droid.presentation.map.data.StoreMapData

@Composable
fun SheetStoreContents(
    modifier: Modifier = Modifier,
    state: LazyListState,
    storeItems: List<StoreMapData>,
    selectStore: (StoreMapData) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .nestedScroll(rememberNestedScrollInteropConnection())
            .heightIn(max = MaxBottomSheetHeight),
        state = state
    ) {
        items(items = storeItems, key = { it.id }) { data: StoreMapData ->
            StoreInfo(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = data.imageUrl ?: "",
                storeName = data.name,
                storeAddress = "${data.city} ${data.district}",
                distance = data.distance,
                favoriteCount = data.favoriteCount,
                onClick = { selectStore(data) },
                imageRadius = ZeroDp
            )
            Spacer(modifier = Modifier.height(height = LargePadding))
        }
    }
}
