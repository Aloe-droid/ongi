package com.aloe_droid.presentation.map.contract

import com.aloe_droid.presentation.base.view.UiContract
import java.util.UUID

sealed class MapEffect : UiContract.SideEffect {
    data class ShowErrorMessage(val message: String) : MapEffect()
    data class NavigateStore(val id: UUID) : MapEffect()
    data object ScrollToFirstPosition: MapEffect()
}
