package com.aloe_droid.ongi.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.home.homeScreen
import com.aloe_droid.presentation.map.contract.route.mapScreen
import com.aloe_droid.presentation.search.contract.route.searchScreen
import com.aloe_droid.presentation.setting.contract.route.settingScreen

@Composable
fun OnGiNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startRoute: UiContract.Route,
    showSnackMessage: (SnackbarVisuals) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startRoute,
        modifier = modifier,
        enterTransition= { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        homeScreen(showSnackMessage = showSnackMessage)

        searchScreen()

        mapScreen()

        settingScreen()
    }
}
