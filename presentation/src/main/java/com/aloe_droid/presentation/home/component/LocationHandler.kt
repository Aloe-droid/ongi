package com.aloe_droid.presentation.home.component

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.GpsHandler
import com.aloe_droid.presentation.base.component.PermissionHandler
import com.aloe_droid.presentation.home.HomeViewModel
import com.aloe_droid.presentation.home.contract.HomeEvent
import com.aloe_droid.presentation.home.contract.HomeUiState
import com.google.android.gms.common.api.ResolvableApiException

@Composable
internal fun LocationHandler(uiState: HomeUiState, viewModel: HomeViewModel) {
    val context: Context = LocalContext.current

    if (uiState.isNeedPermission) {
        PermissionHandler(
            permissions = listOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION),
            onRetry = {
                val event: HomeEvent = HomeEvent.LocationRetry
                viewModel.sendEvent(event = event)
            },
            onSkip = {
                val denyMessage: String = context.getString(R.string.deny_location)
                val event: HomeEvent = HomeEvent.LocationSkip(skipMessage = denyMessage)
                viewModel.sendEvent(event = event)
            }
        )
    }

    uiState.gpsError?.let { error: ResolvableApiException ->
        GpsHandler(
            gpsError = error,
            onEnabled = {
                val event: HomeEvent = HomeEvent.LocationRetry
                viewModel.sendEvent(event = event)
            },
            onDisabled = {
                val denyMessage: String = context.getString(R.string.deny_gps)
                val event: HomeEvent = HomeEvent.LocationSkip(skipMessage = denyMessage)
                viewModel.sendEvent(event = event)
            }
        )
    }
}
