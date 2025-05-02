package com.aloe_droid.presentation.store.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.theme.DefaultImageRatio
import com.aloe_droid.presentation.base.theme.DefaultTextSize
import com.aloe_droid.presentation.base.theme.SemiLargePadding
import com.aloe_droid.presentation.base.theme.SmallPadding
import com.aloe_droid.presentation.base.theme.TitleTextHeight
import com.aloe_droid.presentation.base.theme.TitleTextSize
import com.aloe_droid.presentation.base.theme.toDistance
import com.aloe_droid.presentation.base.theme.toFavorite
import com.aloe_droid.presentation.store.data.StoreData

@Composable
fun StoreTitleInfo(
    modifier: Modifier = Modifier,
    storeData: StoreData,
) {
    Column(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = DefaultImageRatio),
            model = storeData.imageUrl,
            contentDescription = storeData.name,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.store_image_placeholder),
        )

        Spacer(modifier = Modifier.height(height =  SmallPadding))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SemiLargePadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(7f),
                text = storeData.name,
                fontSize = TitleTextSize,
                fontWeight = FontWeight.SemiBold,
                lineHeight = TitleTextHeight
            )

            Row(
                modifier = Modifier.weight(3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star_24px),
                    contentDescription = stringResource(id = R.string.favorite),
                    tint = Color.Yellow
                )

                Text(
                    modifier = Modifier.padding(start = SmallPadding),
                    text = storeData.favoriteCount.toFavorite(),
                    fontSize = DefaultTextSize
                )
            }
        }

        Row(
            modifier = Modifier.padding(horizontal = SemiLargePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(7f),
                text = storeData.address,
                fontSize = DefaultTextSize
            )

            Text(
                modifier = Modifier.weight(3f),
                text = storeData.distance.toDistance(),
                fontSize = DefaultTextSize,
                textAlign = TextAlign.End
            )
        }
    }

}
