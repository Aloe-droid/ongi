package com.aloe_droid.presentation.store.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.ChildCare
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material.icons.outlined.Groups2
import androidx.compose.material.icons.outlined.LocalParking
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Wc
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import com.aloe_droid.domain.entity.StoreDetail
import com.aloe_droid.presentation.R

@Stable
data class StoreDetailData(
    val hasParking: Boolean,
    val hasTakeout: Boolean,
    val hasDelivery: Boolean,
    val hasReservation: Boolean,
    val hasDividedRestroom: Boolean,
    val allowsPets: Boolean,
    val hasWifi: Boolean,
    val hasKidsFacility: Boolean,
    val allowsGroup: Boolean,
) {
    companion object {
        fun StoreDetail.toStoreDetailData() = StoreDetailData(
            hasParking = hasParking,
            hasTakeout = hasTakeout,
            hasDelivery = hasDelivery,
            hasReservation = hasReservation,
            hasDividedRestroom = hasDividedRestroom,
            allowsPets = allowsPets,
            hasWifi = hasWifi,
            hasKidsFacility = hasKidsFacility,
            allowsGroup = allowsGroup
        )

        fun StoreDetailData.toDetailMap() = mapOf<Int, Pair<Boolean, ImageVector>>(
            R.string.parking to (hasParking to Icons.Outlined.LocalParking),
            R.string.take_out to (hasTakeout to Icons.Outlined.ShoppingBag),
            R.string.delivery to (hasDelivery to Icons.Outlined.DeliveryDining),
            R.string.reservation to (hasReservation to Icons.Outlined.AccessTime),
            R.string.dividedRestroom to (hasDividedRestroom to Icons.Outlined.Wc),
            R.string.allows_group to (allowsGroup to Icons.Outlined.Groups2),
            R.string.has_wifi to (hasWifi to Icons.Outlined.Wifi),
            R.string.allows_pets to (allowsPets to Icons.Outlined.Pets),
            R.string.has_kids_facility to (hasKidsFacility to Icons.Outlined.ChildCare)
        )
    }
}
