package com.aloe_droid.presentation.filtered_store.contract

import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.filtered_store.data.StoreDistanceRange
import com.aloe_droid.presentation.filtered_store.data.StoreSortType
import com.aloe_droid.presentation.home.data.StoreData

sealed class FilteredStoreEvent : UiContract.Event {
    data object RefreshEvent : FilteredStoreEvent()
    data class SelectStore(val storeData: StoreData) : FilteredStoreEvent()
    data object ShowOrderBottomSheet : FilteredStoreEvent()
    data object ShowDistanceBottomSheet : FilteredStoreEvent()
    data object CloseBottomSheet : FilteredStoreEvent()
    data class SelectStoreSortType(val sortType: StoreSortType) : FilteredStoreEvent()
    data class SelectDistanceRange(val distanceRange: StoreDistanceRange): FilteredStoreEvent()
}
