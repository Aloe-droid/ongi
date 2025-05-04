package com.aloe_droid.presentation.search.contract

import com.aloe_droid.presentation.base.view.UiContract
import kotlinx.serialization.Serializable

@Serializable
data class Search(val isFromBottomNavigate: Boolean = false) : UiContract.Route {
    companion object {
        const val iS_BOTTOM = "isFromBottomNavigate"
    }
}
