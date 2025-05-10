package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.Location
import com.aloe_droid.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<Location> = locationRepository.getLocation()
}
