package com.aloe_droid.presentation.setting.contract

import com.aloe_droid.presentation.base.view.UiContract

sealed class SettingEffect : UiContract.SideEffect {
    data class ShowErrorMessage(val message: String) : SettingEffect()
    data object NavigateToFilteredStore: SettingEffect()
    data object MoveToPrivacyPolicy: SettingEffect()
    data object MoveToInQueryToDeveloper: SettingEffect()
}
