package com.aloe_droid.presentation.store.contract

import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.store.data.AddressData

sealed class StoreEffect : UiContract.SideEffect {
    data class ShowErrorMessage(val message: String) : StoreEffect()
    data class PopUpWithMessage(val message: String) : StoreEffect()
    data class MoveToCall(val phone: String) : StoreEffect()
    data class MoveToMap(val address: AddressData) : StoreEffect()
}
