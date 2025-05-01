package com.aloe_droid.presentation.filtered_store.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.filtered_store.data.StoreSortType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderBottomSheet(
    onDismissBottomSheet: () -> Unit,
    selectedSortType: StoreSortType,
    onSelectSortType: (StoreSortType) -> Unit
) {
    val sheetState: SheetState = rememberModalBottomSheetState()
    val scope: CoroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismissBottomSheet,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = DefaultPadding),
        ) {
            StoreSortType.entries.forEachIndexed { index: Int, sortType: StoreSortType ->
                val resId: Int = sortType.getNameRes()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                onSelectSortType(sortType)
                            }
                        }
                        .padding(vertical = DefaultPadding),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(id = resId))

                    if (selectedSortType == sortType) {
                        Icon(
                            modifier = Modifier.padding(start = DefaultPadding),
                            imageVector = Icons.Sharp.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                if (index < StoreSortType.entries.lastIndex) {
                    HorizontalDivider(color = Color.LightGray)
                }
            }
        }
    }
}
