package com.aloe_droid.data.datasource.datastore.source

import com.aloe_droid.data.datasource.dto.LocationDTO
import kotlinx.coroutines.flow.Flow

interface LocationDatastore {

    suspend fun saveLocation(locationDTO: LocationDTO)

    fun getLocation(): Flow<LocationDTO?>

    suspend fun clearLocation()
}
