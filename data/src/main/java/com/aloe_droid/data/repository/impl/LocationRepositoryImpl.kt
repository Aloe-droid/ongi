package com.aloe_droid.data.repository.impl

import com.aloe_droid.data.common.Dispatcher
import com.aloe_droid.data.common.DispatcherType
import com.aloe_droid.data.datasource.manager.source.LocationDataSource
import com.aloe_droid.domain.entity.Location
import com.aloe_droid.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource,
    @Dispatcher(DispatcherType.IO) private val ioDispatcher: CoroutineDispatcher
) : LocationRepository {

    override fun getLocation(): Flow<Location> = locationDataSource.getLocation()
        .map { location: android.location.Location ->
            Location(longitude = location.longitude, latitude = location.latitude)
        }.catch { throwable: Throwable ->
            val location = Location(isDefault = true, error = throwable)
            emit(location)
        }.flowOn(ioDispatcher)

}
