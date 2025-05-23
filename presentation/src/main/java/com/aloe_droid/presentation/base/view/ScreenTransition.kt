package com.aloe_droid.presentation.base.view

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object ScreenTransition {
    private const val SLIDE_TIME: Int = 600
    private const val FADE_TIME: Int = 400

    fun slideInFromRight(): EnterTransition =
        slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(SLIDE_TIME)
        )

    fun slideOutToLeft(): ExitTransition =
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(SLIDE_TIME)
        )

    fun slideInFromLeft(): EnterTransition =
        slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(SLIDE_TIME)
        )

    fun slideOutToRight(): ExitTransition =
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(SLIDE_TIME)
        )

    fun fadeInAnim(): EnterTransition = fadeIn(animationSpec = tween(FADE_TIME))
    fun fadeOutAnim(): ExitTransition = fadeOut(animationSpec = tween(FADE_TIME))

}
