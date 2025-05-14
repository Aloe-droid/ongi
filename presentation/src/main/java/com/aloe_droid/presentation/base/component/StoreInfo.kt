package com.aloe_droid.presentation.base.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.theme.DefaultCornerRadius
import com.aloe_droid.presentation.base.theme.DefaultImageRatio
import com.aloe_droid.presentation.base.theme.SmallPadding
import com.aloe_droid.presentation.base.theme.SmallTextSize
import com.aloe_droid.presentation.base.theme.toDistance
import com.aloe_droid.presentation.base.theme.toFavorite

@Composable
fun StoreInfo(
    modifier: Modifier = Modifier,
    imageUrl: String,
    storeName: String,
    storeAddress: String,
    distance: Double,
    favoriteCount: Int,
    imageRatio: Float = DefaultImageRatio,
    imageRadius: Dp = DefaultCornerRadius,
    padding: Dp = SmallPadding,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.clickable(
            indication = null,
            interactionSource = null,
            onClick = onClick
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = imageRatio)
                .clip(shape = RoundedCornerShape(imageRadius)),
            model = imageUrl,
            contentDescription = storeName,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.store_image_placeholder),
            error = painterResource(id = R.drawable.store_image_placeholder),
        )

        Spacer(modifier = Modifier.height(height = padding))

        Row(
            modifier = Modifier
                .padding(horizontal = SmallPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(weight = 10f),
                text = storeName,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(weight = 1f))
            Text(text = distance.toDistance(), fontSize = SmallTextSize)
        }

        Row(
            modifier = Modifier
                .padding(horizontal = SmallPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.star_24px),
                contentDescription = stringResource(id = R.string.favorite),
                tint = Color.Yellow
            )

            Text(
                modifier = Modifier.padding(start = SmallPadding),
                text = favoriteCount.toFavorite(),
                fontSize = SmallTextSize
            )

            Spacer(modifier = Modifier.weight(weight = 1f))

            Text(text = storeAddress, fontSize = SmallTextSize)
        }
    }
}

@Composable
@Preview
fun StoreInfoPreview() {
    StoreInfo(
        storeName = "상호명 이름이 아주 길어져서 여기까지 길어진다면 어떻게 되는지 확인해보세요",
        storeAddress = "시도 시군구",
        distance = 4.5,
        favoriteCount = 99,
        imageUrl = "",
        onClick = {}
    )
}
