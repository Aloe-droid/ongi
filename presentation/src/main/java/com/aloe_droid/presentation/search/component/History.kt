package com.aloe_droid.presentation.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.paging.compose.LazyPagingItems
import com.aloe_droid.domain.entity.SearchHistory
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.IconText
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.LargeTextSize
import com.aloe_droid.presentation.base.theme.SemiSmallTextSize
import com.aloe_droid.presentation.base.theme.SmallPadding
import com.aloe_droid.presentation.base.theme.toTimeStamp

@Composable
fun Histories(
    modifier: Modifier = Modifier,
    historyItems: LazyPagingItems<SearchHistory>,
    deleteAllHistory: () -> Unit,
    deleteHistory: (Long) -> Unit,
    onSearch: (String) -> Unit,
) {
    Column(modifier = modifier) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = DefaultPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.recent_search),
                fontSize = LargeTextSize,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                modifier = Modifier.clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = deleteAllHistory
                ),
                text = stringResource(id = R.string.delete_all)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(count = historyItems.itemCount) { index: Int ->
                val item: SearchHistory = historyItems[index] ?: return@items
                History(
                    modifier = Modifier.fillMaxWidth(),
                    keyword = item.keyword,
                    timestamp = item.timestamp.toTimeStamp(),
                    deleteHistory = { deleteHistory(item.id) },
                    onSearch = onSearch
                )
            }
        }
    }
}

@Composable
fun History(
    modifier: Modifier = Modifier,
    keyword: String,
    timestamp: String,
    deleteHistory: () -> Unit,
    onSearch: (String) -> Unit
) {
    val iconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)

    IconText(
        modifier = modifier,
        text = keyword,
        leadingIcon = {
            Image(
                imageVector = Icons.Outlined.History,
                contentDescription = keyword,
                colorFilter = ColorFilter.tint(color = iconColor)
            )
        },
        trailingIcon = {
            Text(text = timestamp, color = iconColor, fontSize = SemiSmallTextSize)
            Image(
                modifier = Modifier
                    .padding(start = SmallPadding)
                    .clickable(
                        indication = null,
                        interactionSource = null,
                        onClick = deleteHistory
                    ),
                imageVector = Icons.Outlined.Close,
                contentDescription = keyword,
                colorFilter = ColorFilter.tint(color = iconColor)
            )
        },
        onClick = {
            onSearch(keyword)
        }
    )
}
