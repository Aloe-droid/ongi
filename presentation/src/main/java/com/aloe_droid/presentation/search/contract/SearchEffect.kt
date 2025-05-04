package com.aloe_droid.presentation.search.contract

import com.aloe_droid.presentation.base.view.UiContract
import java.util.UUID

sealed class SearchEffect : UiContract.SideEffect {
    data class ShowErrorMessage(val message: String) : SearchEffect()
    data class SelectStore(val storeId: UUID) : SearchEffect()
    data class NavigateToFilteredStoreList(val query: String) : SearchEffect()
    data object NavigateUp: SearchEffect()
}
