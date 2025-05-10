package com.aloe_droid.presentation.map.component

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.GpsHandler
import com.aloe_droid.presentation.base.component.PermissionHandler
import com.aloe_droid.presentation.map.MapViewModel
import com.aloe_droid.presentation.map.contract.MapEvent
import com.aloe_droid.presentation.map.contract.MapUiState
import com.google.android.gms.common.api.ResolvableApiException

@Composable
internal fun LocationHandler(uiState: MapUiState, viewModel: MapViewModel) {
    val context: Context = LocalContext.current

    if (uiState.isNeedPermission) {
        PermissionHandler(
            permissions = listOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION),
            onRetry = {
                val event: MapEvent = MapEvent.LocationRetry
                viewModel.sendEvent(event = event)
            },
            onSkip = {
                val denyMessage: String = context.getString(R.string.deny_location)
                val event: MapEvent = MapEvent.LocationSkip(skipMessage = denyMessage)
                viewModel.sendEvent(event = event)
            }
        )
    }

    uiState.gpsError?.let { error: ResolvableApiException ->
        GpsHandler(
            gpsError = error,
            onEnabled = {
                val event: MapEvent = MapEvent.LocationRetry
                viewModel.sendEvent(event = event)
            },
            onDisabled = {
                val denyMessage: String = context.getString(R.string.deny_gps)
                val event: MapEvent = MapEvent.LocationSkip(skipMessage = denyMessage)
                viewModel.sendEvent(event = event)
            }
        )
    }
}
