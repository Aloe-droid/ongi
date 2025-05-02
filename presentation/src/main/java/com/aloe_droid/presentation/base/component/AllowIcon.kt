package com.aloe_droid.presentation.base.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.ExtraLargeIconSize
import com.aloe_droid.presentation.base.theme.SmallPadding

@Composable
fun AllowIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    text: String,
    isAllow: Boolean,
) {
    val color = if (isAllow) MaterialTheme.colorScheme.secondary else Color.LightGray

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(ExtraLargeIconSize)
                .clip(CircleShape)
                .background(color = color),
            contentAlignment = Alignment.Center
        ) {
            Image(imageVector = imageVector, contentDescription = text)
        }

        Spacer(modifier = Modifier.height(height = SmallPadding))
        Text(text = text)
        Spacer(modifier = Modifier.height(height = DefaultPadding))
    }
}

@Composable
@Preview
fun AllowIconPreview() {
    AllowIcon(
        imageVector = Icons.Default.DeliveryDining,
        text = "배달",
        isAllow = true
    )
}
