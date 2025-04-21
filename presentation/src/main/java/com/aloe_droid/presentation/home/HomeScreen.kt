package com.aloe_droid.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aloe_droid.presentation.base.BaseSnackBarVisuals

@Composable
fun HomeScreen(showSnackMessage: (SnackbarVisuals) -> Unit) {
    LaunchedEffect(Unit) {
        showSnackMessage(BaseSnackBarVisuals("여기는 홈"))
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("여기는 홈")
    }
}
