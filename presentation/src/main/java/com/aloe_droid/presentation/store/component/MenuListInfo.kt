package com.aloe_droid.presentation.store.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.LLargeTextSize
import com.aloe_droid.presentation.base.theme.SemiLargePadding
import com.aloe_droid.presentation.base.theme.SmallPadding
import com.aloe_droid.presentation.base.theme.toWon
import com.aloe_droid.presentation.store.data.MenuData
import com.aloe_droid.presentation.store.data.StoreData

@Composable
fun MenuListInfo(
    modifier: Modifier = Modifier,
    storeData: StoreData
) {
    Column(modifier = modifier.padding(horizontal = SemiLargePadding)) {
        Text(
            text = storeData.category,
            fontSize = LLargeTextSize,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis
        )

        storeData.menuList.forEach { menu: MenuData ->
            MenuInfo(modifier = Modifier.fillMaxWidth(), menuData = menu)
            Spacer(modifier = Modifier.height(height = SmallPadding))
        }
    }
}

@Composable
private fun MenuInfo(modifier: Modifier = Modifier, menuData: MenuData) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {

        Text(menuData.name)

        DashedDivider(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = DefaultPadding)
        )

        Text(text = menuData.price.toWon())
    }
}

@Composable
private fun DashedDivider(
    modifier: Modifier = Modifier,
    dashWidth: Dp = 2.dp,
    dashHeight: Dp = 2.dp,
    gapWidth: Dp = 6.dp,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Canvas(modifier) {

        val pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashWidth.toPx(), gapWidth.toPx()),
            phase = 0f
        )

        drawLine(
            color = color,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            pathEffect = pathEffect,
            strokeWidth = dashHeight.toPx()
        )
    }
}
