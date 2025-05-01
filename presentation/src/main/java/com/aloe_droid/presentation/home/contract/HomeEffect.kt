package com.aloe_droid.presentation.home.contract

import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.filtered_store.data.StoreFilter
import java.util.UUID

sealed class HomeEffect : UiContract.SideEffect {
    data class ShowErrorMessage(val message: String) : HomeEffect()
    data class ShowBrowser(val url: String) : HomeEffect()
    data class NavigateStore(val id: UUID) : HomeEffect()
    data class NavigateStoreList(val filter: StoreFilter) : HomeEffect()
}
