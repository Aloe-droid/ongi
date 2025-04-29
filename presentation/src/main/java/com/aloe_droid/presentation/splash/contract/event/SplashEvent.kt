package com.aloe_droid.presentation.splash.contract.event

import com.aloe_droid.presentation.base.view.UiContract

sealed class SplashEvent : UiContract.Event {
    data object CheckAuth : SplashEvent()
}
