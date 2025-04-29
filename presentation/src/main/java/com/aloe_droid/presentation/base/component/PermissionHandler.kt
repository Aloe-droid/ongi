package com.aloe_droid.presentation.base.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import com.aloe_droid.presentation.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

enum class GRANTED {
    ANY, ALL
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    permissions: List<String>,
    checkGranted: GRANTED = GRANTED.ANY,
    onRetry: () -> Unit = {},
    onSkip: () -> Unit = {},
) {
    val (showDialog, setShowDialog) = rememberSaveable { mutableStateOf(false) }
    val permissionState: MultiplePermissionsState = rememberMultiplePermissionsState(permissions) {
        val isGranted: Boolean = when (checkGranted) {
            GRANTED.ANY -> it.values.any { it }
            GRANTED.ALL -> it.values.all { it }
        }

        if (isGranted) onRetry()
        else setShowDialog(true)
    }

    LaunchedEffect(key1 = permissionState.permissions) {
        val granted: Boolean = when (checkGranted) {
            GRANTED.ANY -> permissionState.permissions.any { it.status.isGranted }
            GRANTED.ALL -> permissionState.permissions.all { it.status.isGranted }
        }
        val shouldShow: Boolean = permissionState.shouldShowRationale

        // 1. 권한이 허락 된 경우 
        // 2. 권한을 요청했지만 거절한 경우
        // 3. 권한이 거절 된 경우
        if (granted) onRetry()
        else if (!shouldShow) permissionState.launchMultiplePermissionRequest()
        else onSkip()
    }

    if (showDialog) {
        val context: Context = LocalContext.current
        val uri: Uri = "package:${context.packageName}".toUri()
        val intent: Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(uri)
        val contracts = ActivityResultContracts.StartActivityForResult()
        val launcher = rememberLauncherForActivityResult(contract = contracts) {
            onRetry()
        }

        CustomLocationPermissionDialog(
            onConfirm = { launcher.launch(intent) },
            onDismiss = { onSkip() }
        )
    }
}


@Composable
fun CustomLocationPermissionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.location_dialog_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val appName = stringResource(R.string.location_dialog_app_name)
                    val fullDescription = stringResource(R.string.location_dialog_description)

                    Text(
                        text = buildAnnotatedString {
                            val description = fullDescription.replace("'온기'", "'$appName'")
                            val startIndex = description.indexOf("'$appName'")
                            val endIndex = startIndex + appName.length + 2

                            if (startIndex >= 0) {
                                append(description.substring(0, startIndex))
                                append("'")
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(appName)
                                }
                                append("'")
                                append(description.substring(endIndex))
                            } else {
                                append(description)
                            }
                        },
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.location_dialog_confirm),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.location_dialog_dismiss),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = stringResource(R.string.location_dialog_privacy_info),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}
