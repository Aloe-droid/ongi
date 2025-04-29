package com.aloe_droid.presentation.setting.contract.route

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.setting.SettingScreen
import com.aloe_droid.presentation.setting.SettingViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Setting : UiContract.Route


fun NavGraphBuilder.settingScreen() = composable<Setting> {
    val viewModel: SettingViewModel = hiltViewModel()
    SettingScreen()
}