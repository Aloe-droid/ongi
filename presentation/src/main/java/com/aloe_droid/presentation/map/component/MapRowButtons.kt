package com.aloe_droid.presentation.map.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import com.aloe_droid.presentation.base.theme.DefaultBorderDp
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.ExtraLargePadding

@Composable
fun MapRowButtons(onLocationCheck: () -> Unit, onSearch: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ExtraLargePadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = DefaultPadding),
            horizontalAlignment = Alignment.End
        ) {
            val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            val iconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

            Box(
                modifier = Modifier
                    .border(
                        border = BorderStroke(DefaultBorderDp, borderColor),
                        shape = RectangleShape
                    )
                    .background(color = MaterialTheme.colorScheme.surface)
                    .clickable { onLocationCheck() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.padding(DefaultPadding),
                    imageVector = Icons.Default.LocationSearching,
                    contentDescription = null,
                    tint = iconColor
                )
            }

            Box(
                modifier = Modifier
                    .border(
                        border = BorderStroke(DefaultBorderDp, borderColor),
                        shape = RectangleShape
                    )
                    .background(color = MaterialTheme.colorScheme.surface)
                    .clickable { onSearch() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.padding(DefaultPadding),
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = iconColor
                )
            }
        }
    }
}
