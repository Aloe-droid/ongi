package com.aloe_droid.presentation.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.aloe_droid.presentation.base.component.IconText
import com.aloe_droid.presentation.base.theme.LargeIconSize
import com.aloe_droid.presentation.base.theme.toDistance
import com.aloe_droid.presentation.filtered_store.data.StoreCategory
import com.aloe_droid.presentation.search.data.SearchedStore.Companion.toIconRes
import java.util.UUID

@Composable
fun SearchedStoreScreen(
    modifier: Modifier = Modifier,
    id: UUID,
    name: String,
    distance: Double,
    category: StoreCategory,
    onClick: (UUID) -> Unit,
    iconSize: Dp = LargeIconSize
) {
    IconText(
        modifier = modifier,
        text = name,
        onClick = { onClick(id) },
        leadingIcon = {
            Box(
                modifier = Modifier.size(iconSize),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize * 0.7f),
                    painter = painterResource(id = category.toIconRes()),
                    contentDescription = name,
                    tint = Color.Unspecified
                )
            }
        },
        trailingIcon = {
            Text(
                text = distance.toDistance(),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    )
}
