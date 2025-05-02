package com.aloe_droid.presentation.store.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aloe_droid.presentation.base.component.AllowIcon
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.SemiLargePadding
import com.aloe_droid.presentation.store.data.StoreData
import com.aloe_droid.presentation.store.data.StoreDetailData.Companion.toDetailMap

@Composable
fun StoreDetailInfo(
    modifier: Modifier = Modifier,
    storeData: StoreData
) {
    FlowRow(
        modifier
            .fillMaxWidth()
            .padding(horizontal = SemiLargePadding)
            .padding(top = DefaultPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.Center
    ) {
        for ((textRes, pair) in storeData.detail.toDetailMap()) {
            val (isAllow, vector) = pair
            AllowIcon(
                imageVector = vector,
                text = stringResource(id = textRes),
                isAllow = isAllow
            )
        }
    }
}
