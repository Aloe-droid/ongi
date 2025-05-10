package com.aloe_droid.presentation.map.data

import androidx.compose.runtime.Stable
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.presentation.map.util.LocationUtil
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng
import java.util.UUID

@Stable
data class StoreMapData(
    val id: UUID,
    val name: String,
    val address: String,
    val favoriteCount: Int,
    val latitude: Double,
    val longitude: Double,
    val distance: Double,
    val category: String,
    val city: String,
    val district: String,
    val phone: String?,
    val imageUrl: String?
) : TedClusterItem {

    override fun getTedLatLng(): TedLatLng {
        return TedLatLng(latitude = latitude, longitude = longitude)
    }

    companion object {
        fun List<Store>.toStoreMapDataList(
            myLat: Double? = null,
            myLon: Double? = null
        ): List<StoreMapData> = map {
            it.toStoreMapData(myLat = myLat, myLon = myLon)
        }.sortedBy { it.distance }

        fun Store.toStoreMapData(myLat: Double? = null, myLon: Double? = null) = StoreMapData(
            id = id,
            name = name,
            address = address,
            favoriteCount = favoriteCount,
            latitude = latitude,
            longitude = longitude,
            distance = if (myLat == null || myLon == null) {
                distance
            } else {
                LocationUtil.calculateDistanceInKm(
                    lat1 = latitude,
                    lon1 = longitude,
                    lat2 = myLat,
                    lon2 = myLon
                )
            },
            category = category,
            city = city,
            district = district,
            phone = phone,
            imageUrl = imageUrl
        )
    }
}
