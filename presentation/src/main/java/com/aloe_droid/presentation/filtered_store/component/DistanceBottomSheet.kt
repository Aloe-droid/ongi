package com.aloe_droid.presentation.filtered_store.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.theme.DefaultCornerRadius
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.LargePadding
import com.aloe_droid.presentation.base.theme.toSelectDistanceString
import com.aloe_droid.presentation.filtered_store.data.StoreDistanceRange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.enums.EnumEntries
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistanceBottomSheet(
    onDismissBottomSheet: () -> Unit,
    selectedDistanceRange: StoreDistanceRange,
    onSelectDistanceRange: (StoreDistanceRange) -> Unit,
) {
    val sheetState: SheetState = rememberModalBottomSheetState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val ranges: EnumEntries<StoreDistanceRange> = StoreDistanceRange.entries
    val (sliderIndex, setSliderIndex) = remember {
        mutableIntStateOf(value = ranges.indexOf(selectedDistanceRange))
    }

    ModalBottomSheet(
        onDismissRequest = onDismissBottomSheet,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DefaultPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = ranges[sliderIndex].toSelectDistanceString())

            Spacer(modifier = Modifier.height(DefaultPadding))

            Slider(
                modifier = Modifier.padding(horizontal = DefaultPadding),
                value = sliderIndex.toFloat(),
                onValueChange = { setSliderIndex(it.roundToInt()) },
                valueRange = 0f..(ranges.size - 1).toFloat(),
                steps = ranges.size - 2,
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondary,
                    activeTickColor = MaterialTheme.colorScheme.background,
                    inactiveTickColor = MaterialTheme.colorScheme.background
                )
            )

            Spacer(modifier = Modifier.height(LargePadding))

            Button(
                shape = RoundedCornerShape(size = DefaultCornerRadius),
                onClick = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        onSelectDistanceRange(ranges[sliderIndex])
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.select))
            }
        }
    }
}
