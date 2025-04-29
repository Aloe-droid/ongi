package com.aloe_droid.domain.repository

import com.aloe_droid.domain.entity.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocation() : Flow<Location>
}
