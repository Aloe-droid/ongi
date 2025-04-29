package com.aloe_droid.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.StoreInfo
import com.aloe_droid.presentation.base.theme.DefaultLogoImageSize
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.LargePadding
import com.aloe_droid.presentation.base.theme.SmallPadding
import com.aloe_droid.presentation.home.data.StoreData

@Composable
fun FavoriteStoreListView(
    modifier: Modifier = Modifier,
    favoriteStoreList: List<StoreData>,
    selectStore: (StoreData) -> Unit,
    clickFavorite: () -> Unit
) {
    Column(modifier = modifier.padding(start = LargePadding)) {
        ForwardText(
            modifier = Modifier.padding(end = SmallPadding),
            text = stringResource(id = R.string.favorite_stores),
            onClick = clickFavorite
        )

        Spacer(modifier = Modifier.height(height = DefaultPadding))

        LazyRow(
            modifier = Modifier.padding(horizontal = SmallPadding),
            horizontalArrangement = Arrangement.spacedBy(LargePadding),
            contentPadding = PaddingValues(end = LargePadding)
        ) {
            items(favoriteStoreList, key = { it.id }) { data: StoreData ->
                StoreInfo(
                    modifier = Modifier.width(width = DefaultLogoImageSize),
                    imageUrl = data.imageUrl ?: "",
                    storeName = data.name,
                    storeAddress = "${data.city} ${data.district}",
                    distance = data.distance,
                    favoriteCount = data.favoriteCount,
                    onClick = { selectStore(data) }
                )
            }
        }
    }
}