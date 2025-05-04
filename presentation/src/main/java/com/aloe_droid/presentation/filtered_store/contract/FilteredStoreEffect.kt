package com.aloe_droid.presentation.filtered_store.contract

import com.aloe_droid.presentation.base.view.UiContract
import java.util.UUID

sealed class FilteredStoreEffect : UiContract.SideEffect {
    data class ShowErrorMessage(val message: String) : FilteredStoreEffect()
    data class NavigateStore(val id: UUID) : FilteredStoreEffect()
    data object ScrollToFirstPosition : FilteredStoreEffect()
}
