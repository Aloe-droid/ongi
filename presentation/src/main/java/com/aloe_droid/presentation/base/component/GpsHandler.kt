package com.aloe_droid.presentation.base.component

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.android.gms.common.api.ResolvableApiException

@Composable
fun GpsHandler(
    gpsError: ResolvableApiException,
    onEnabled: () -> Unit = {},
    onDisabled: () -> Unit = {},
) {
    val (gpsState, setGpsState) = rememberSaveable { mutableStateOf(false) }
    val contract = ActivityResultContracts.StartIntentSenderForResult()
    val launcher = rememberLauncherForActivityResult(contract = contract) { result ->
        val isGranted = result.resultCode == Activity.RESULT_OK
        if (isGranted) onEnabled()
        else onDisabled()
    }

    LaunchedEffect(key1 = gpsState) {
        if (!gpsState) {
            val request = IntentSenderRequest.Builder(gpsError.resolution).build()
            launcher.launch(request)
            setGpsState(true)
        }
    }
}
