package com.aloe_droid.presentation.base.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.theme.DefaultImageSize
import com.aloe_droid.presentation.base.theme.SmallPadding
import com.aloe_droid.presentation.base.theme.SmallTextSize

@Composable
fun Category(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int,
    name: String,
    imageSize: Dp = DefaultImageSize,
    textSize: TextUnit = SmallTextSize,
    spaceSize: Dp = SmallPadding,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.clickable(
            onClick = onClick,
            interactionSource = null,
            indication = null
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(imageSize),
            painter = painterResource(id = imageRes),
            contentDescription = name
        )
        Spacer(modifier = Modifier.height(spaceSize))
        Text(text = name, fontSize = textSize)
    }
}

@Composable
@Preview
fun LogoPreview() {
    Category(
        imageRes = R.drawable.korean_food,
        name = "한식",
    )
}