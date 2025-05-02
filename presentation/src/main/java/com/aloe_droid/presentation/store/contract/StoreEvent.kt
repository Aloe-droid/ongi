package com.aloe_droid.presentation.store.contract

import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.store.data.AddressData

sealed class StoreEvent : UiContract.Event {
    data object LoadEvent : StoreEvent()
    data class CantFindStoreEvent(val message: String) : StoreEvent()
    data object ToggleFavorite : StoreEvent()
    data class CallEvent(val phone: String) : StoreEvent()
    data class MapEvent(val address: AddressData) : StoreEvent()
}
