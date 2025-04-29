package com.aloe_droid.presentation.splash.contract.effect

import com.aloe_droid.presentation.base.view.UiContract

sealed class SplashEffect : UiContract.SideEffect {
    data class FinishSplashScreen(val throwable: Throwable) : SplashEffect()
}
