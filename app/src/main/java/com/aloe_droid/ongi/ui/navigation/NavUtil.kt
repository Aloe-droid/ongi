package com.aloe_droid.ongi.ui.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

class NavUtil private constructor() {
    companion object {
        fun NavHostController.safeMove(move: NavHostController.() -> Unit) {
            val backStackEntry: NavBackStackEntry = currentBackStackEntry ?: return
            val lifecycle: Lifecycle.State = backStackEntry.lifecycle.currentState
            if (lifecycle == Lifecycle.State.RESUMED) move()
        }
    }
}
