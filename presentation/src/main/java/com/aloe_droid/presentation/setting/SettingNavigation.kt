package com.aloe_droid.presentation.setting

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aloe_droid.presentation.BuildConfig
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.LoadingScreen
import com.aloe_droid.presentation.base.view.BaseSnackBarVisuals
import com.aloe_droid.presentation.base.view.CollectSideEffects
import com.aloe_droid.presentation.base.view.ScreenTransition
import com.aloe_droid.presentation.filtered_store.contract.FilteredStore
import com.aloe_droid.presentation.setting.contract.Setting
import com.aloe_droid.presentation.setting.contract.SettingEffect
import com.aloe_droid.presentation.setting.contract.SettingEvent
import com.aloe_droid.presentation.setting.contract.SettingUiData
import com.aloe_droid.presentation.setting.contract.SettingUiState

fun NavGraphBuilder.settingScreen(
    showSnackMessage: (SnackbarVisuals) -> Unit,
    navigateToFilteredStoreWithFavorite: () -> Unit,
) = composable<Setting>(
    enterTransition = {
        ScreenTransition.slideInFromRight()
    },
    popEnterTransition = {
        if (initialState.destination.hasRoute<FilteredStore>()) ScreenTransition.slideInFromLeft()
        else ScreenTransition.slideInFromRight()
    },
    exitTransition = {
        if (targetState.destination.hasRoute<FilteredStore>()) ScreenTransition.slideOutToLeft()
        else ScreenTransition.slideOutToRight()
    },
    popExitTransition = {
        ScreenTransition.slideOutToRight()
    }
) {
    val context: Context = LocalContext.current
    val viewModel: SettingViewModel = hiltViewModel()
    val uiState: SettingUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiData: SettingUiData by viewModel.uiData.collectAsStateWithLifecycle()

    CollectSideEffects(effectFlow = viewModel.uiEffect) { sideEffect: SettingEffect ->
        when (sideEffect) {
            is SettingEffect.ShowErrorMessage -> {
                val snackBarVisuals = BaseSnackBarVisuals(message = sideEffect.message)
                showSnackMessage(snackBarVisuals)
            }

            SettingEffect.NavigateToFilteredStore -> {
                navigateToFilteredStoreWithFavorite()
            }

            SettingEffect.MoveToPrivacyPolicy -> {
                CustomTabsIntent.Builder()
                    .build()
                    .launchUrl(context, BuildConfig.PRIAVACY_SECURITY.toUri())
            }

            SettingEffect.MoveToInQueryToDeveloper -> {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:${BuildConfig.EMAIL}".toUri()
                }
                context.startActivity(intent)
            }

            SettingEffect.MoveToLocationAuth -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = "package:${context.packageName}".toUri()
                }
                context.startActivity(intent)
            }
        }
    }

    if (uiState.isInitialState) {
        LoadingScreen(content = stringResource(id = R.string.loading))
    } else {
        SettingScreen(
            storeCount = uiData.storeCount,
            syncTime = uiData.syncTime,
            onClickFavoriteStore = {
                val event = SettingEvent.ClickFavoriteStore
                viewModel.sendEvent(event = event)
            },
            onClickPrivacyPolicy = {
                val event = SettingEvent.ClickPrivacyPolicy
                viewModel.sendEvent(event = event)
            },
            onClickInQueryToDeveloper = {
                val event = SettingEvent.ClickInquiryToDeveloper
                viewModel.sendEvent(event = event)
            },
            onClickLocationAuth = {
                val event = SettingEvent.ClickLocationAuth
                viewModel.sendEvent(event = event)
            }
        )
    }
}
