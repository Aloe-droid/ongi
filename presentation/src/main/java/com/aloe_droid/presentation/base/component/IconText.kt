package com.aloe_droid.presentation.base.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.LargeIconSize

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    text: String,
    iconRes: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = DefaultPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(LargeIconSize)
                .clip(CircleShape),
            painter = painterResource(id = iconRes),
            contentDescription = text,
            tint = Color.Unspecified
        )

        Text(text = text, modifier = Modifier.padding(start = DefaultPadding))
    }
}

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    text: String,
    leadingIcon: @Composable RowScope.() -> Unit,
    trailingIcon: @Composable RowScope.() -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = DefaultPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon()

        Text(
            text = text,
            modifier = Modifier
                .padding(DefaultPadding)
                .weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        trailingIcon()
    }
}


@Composable
@Preview
fun IconTextPreview() {
    IconText(
        text = "010-1234-5678",
        iconRes = R.drawable.call,
        onClick = {}
    )
}
