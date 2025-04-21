package com.aloe_droid.presentation.home.contract.route

import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aloe_droid.presentation.base.UiContract
import com.aloe_droid.presentation.home.HomeScreen
import com.aloe_droid.presentation.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Home : UiContract.Route

fun NavGraphBuilder.homeScreen(showSnackMessage: (SnackbarVisuals) -> Unit) = composable<Home> {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(showSnackMessage = showSnackMessage)
}
