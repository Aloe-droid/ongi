package com.aloe_droid.data.datasource.manager.source

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    fun getLocation() : Flow<Location>
}