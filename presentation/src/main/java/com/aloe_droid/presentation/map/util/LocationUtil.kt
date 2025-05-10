package com.aloe_droid.presentation.map.util

import android.location.Location
import com.aloe_droid.presentation.home.data.LocationData
import com.naver.maps.geometry.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object LocationUtil {
    fun getRadiusKm(northEast: LatLng, southWest: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            northEast.latitude, northEast.longitude,
            southWest.latitude, southWest.longitude,
            results
        )
        return results[0] / 1000f
    }

    fun LatLng.toLocationData() = LocationData(
        latitude = latitude,
        longitude = longitude
    )

    fun calculateDistanceInKm(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val earthRadiusKm = 6371.0 // 지구 반지름 (단위: km)

        // 위도와 경도의 차이 (라디안 단위로 변환)
        val deltaLat = Math.toRadians(lat2 - lat1)
        val deltaLon = Math.toRadians(lon2 - lon1)

        // 위도 각도도 라디안으로 변환
        val radLat1 = Math.toRadians(lat1)
        val radLat2 = Math.toRadians(lat2)

        // 하버사인 공식의 핵심 부분 (반(半)현 코사인 법칙)
        val haversineFormula = sin(deltaLat / 2).pow(2) +
                cos(radLat1) * cos(radLat2) *
                sin(deltaLon / 2).pow(2)

        // 두 지점 사이의 중심각 (radian)
        val centralAngle = 2 * atan2(sqrt(haversineFormula), sqrt(1 - haversineFormula))

        // 거리 = 중심각 * 지구 반지름
        return earthRadiusKm * centralAngle
    }
}
