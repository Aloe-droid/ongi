package com.aloe_droid.presentation.map.contract.route

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.map.MapScreen
import com.aloe_droid.presentation.map.MapViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Map : UiContract.Route


fun NavGraphBuilder.mapScreen() = composable<Map> {
    val mapViewModel: MapViewModel = hiltViewModel()
    MapScreen()
}
