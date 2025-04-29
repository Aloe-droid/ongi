package com.aloe_droid.presentation.base.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.aloe_droid.presentation.base.theme.DefaultPadding
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    spaceSize: Dp = DefaultPadding,
    content: String? = null,
    maxDotCount: Int = 4,
    progressIndicator: @Composable () -> Unit = { CircularProgressIndicator() }
) {
    val (dotCount, setDotCount) = rememberSaveable { mutableIntStateOf(0) }
    LaunchedEffect(key1 = content, key2 = dotCount) {
        while (true) {
            delay(500)
            setDotCount((dotCount + 1) % maxDotCount)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        progressIndicator()
        content?.let {
            Spacer(modifier = Modifier.height(height = spaceSize))
            Text(text = "$content${".".repeat(n = dotCount)}")
        }
    }
}