package com.aloe_droid.presentation.filtered_store.component

import androidx.compose.runtime.Composable
import com.aloe_droid.presentation.filtered_store.data.StoreDistanceRange
import com.aloe_droid.presentation.filtered_store.data.StoreSortType

@Composable
fun FilterBottomSheet(
    isShowOrderBottomSheet: Boolean,
    isShowDistanceBottomSheet: Boolean,
    selectedSortType: StoreSortType,
    selectedDistanceRange: StoreDistanceRange,
    onSelectSortType: (StoreSortType) -> Unit,
    onSelectDistanceRange: (StoreDistanceRange) -> Unit,
    onDismissBottomSheet: () -> Unit
) {
    if (isShowOrderBottomSheet) {
        OrderBottomSheet(
            onDismissBottomSheet = onDismissBottomSheet,
            selectedSortType = selectedSortType,
            onSelectSortType = onSelectSortType
        )
    } else if (isShowDistanceBottomSheet) {
        DistanceBottomSheet(
            onDismissBottomSheet = onDismissBottomSheet,
            selectedDistanceRange = selectedDistanceRange,
            onSelectDistanceRange = onSelectDistanceRange
        )
    }
}
