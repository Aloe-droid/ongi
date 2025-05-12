package com.aloe_droid.presentation.setting.contract

import com.aloe_droid.presentation.base.view.UiContract

sealed class SettingEvent : UiContract.Event {
    data object ClickFavoriteStore : SettingEvent()
    data object ClickPrivacyPolicy : SettingEvent()
    data object ClickInquiryToDeveloper : SettingEvent()
}
