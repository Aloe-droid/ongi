package com.aloe_droid.presentation.filtered_store.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.SmallPadding

@Composable
fun FilterRow(
    modifier: Modifier = Modifier,
    order: String,
    setShowOrderBottomSheet: () -> Unit,
    distance: String,
    setShowDistanceBottomSheet: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        Filter(
            text = order.toString(),
            imageVector = Icons.Sharp.ArrowDropDown,
            onClick = setShowOrderBottomSheet
        )

        Spacer(modifier = Modifier.padding(horizontal = SmallPadding))

        Filter(
            text = distance.toString(),
            imageVector = Icons.Sharp.ArrowDropDown,
            onClick = setShowDistanceBottomSheet
        )
    }
}

@Composable
fun Filter(text: String, imageVector: ImageVector, onClick: () -> Unit) {
    OutlinedCard(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(vertical = SmallPadding, horizontal = DefaultPadding)) {
            Text(text = text, modifier = Modifier.weight(1f, fill = false))
            Icon(
                modifier = Modifier.padding(start = SmallPadding),
                imageVector = imageVector, contentDescription = null
            )
        }
    }
}