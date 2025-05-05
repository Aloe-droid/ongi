package com.aloe_droid.presentation.setting

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aloe_droid.presentation.BuildConfig
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.LoadingScreen
import com.aloe_droid.presentation.base.view.BaseSnackBarVisuals
import com.aloe_droid.presentation.base.view.CollectSideEffects
import com.aloe_droid.presentation.setting.contract.Setting
import com.aloe_droid.presentation.setting.contract.SettingEffect
import com.aloe_droid.presentation.setting.contract.SettingEvent
import com.aloe_droid.presentation.setting.contract.SettingUiState

fun NavGraphBuilder.settingScreen(
    showSnackMessage: (SnackbarVisuals) -> Unit,
    navigateToFilteredStoreWithFavorite: () -> Unit,
) = composable<Setting> {
    val context: Context = LocalContext.current
    val viewModel: SettingViewModel = hiltViewModel()
    val uiState: SettingUiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        }
    }

    if (uiState.isInitialState) {
        viewModel.sendEvent(event = SettingEvent.LoadEvent)
        LoadingScreen(content = stringResource(id = R.string.loading))
    } else {
        SettingScreen(
            storeCount = uiState.storeCount,
            syncTime = uiState.syncTime,
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
            }
        )
    }
}
