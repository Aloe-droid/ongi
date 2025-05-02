package com.aloe_droid.presentation.store.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.IconText
import com.aloe_droid.presentation.base.theme.SemiLargePadding
import com.aloe_droid.presentation.store.data.AddressData
import com.aloe_droid.presentation.store.data.StoreData

@Composable
fun OpenExternalApp(
    modifier: Modifier = Modifier,
    storeData: StoreData,
    onCallClick: (String) -> Unit,
    onAddressClick: (AddressData) -> Unit
) {
    Column(modifier = modifier.padding(horizontal = SemiLargePadding)) {

        IconText(
            text = storeData.phone ?: stringResource(id = R.string.no_call),
            iconRes = R.drawable.call
        ) {
            storeData.phone?.let { onCallClick(it) }
        }

        IconText(
            text = storeData.address,
            iconRes = R.drawable.map
        ) {
            val addressData = AddressData(
                name = storeData.name,
                address = storeData.address,
                latitude = storeData.latitude,
                longitude = storeData.longitude
            )
            onAddressClick(addressData)
        }
    }
}
