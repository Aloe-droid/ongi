package com.aloe_droid.presentation.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aloe_droid.presentation.base.theme.LargePadding
import com.aloe_droid.presentation.store.component.MenuListInfo
import com.aloe_droid.presentation.store.component.OpenExternalApp
import com.aloe_droid.presentation.store.component.StoreDetailInfo
import com.aloe_droid.presentation.store.component.StoreTitleInfo
import com.aloe_droid.presentation.store.data.AddressData
import com.aloe_droid.presentation.store.data.StoreData

@Composable
fun StoreScreen(
    modifier: Modifier = Modifier,
    storeData: StoreData,
    onCallClick: (String) -> Unit,
    onAddressClick: (AddressData) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .background(color = MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.spacedBy(LargePadding)
    ) {

        StoreTitleInfo(storeData = storeData)

        MenuListInfo(storeData = storeData)

        StoreDetailInfo(storeData = storeData)

        OpenExternalApp(
            storeData = storeData,
            onCallClick = onCallClick,
            onAddressClick = onAddressClick
        )

        Spacer(modifier = Modifier.height(height = LargePadding))
    }
}
