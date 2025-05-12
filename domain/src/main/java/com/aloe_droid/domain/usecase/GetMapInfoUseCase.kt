package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.Location
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.entity.StoreMapEntity
import com.aloe_droid.domain.entity.StoreQuery
import com.aloe_droid.domain.entity.StoreQueryCategory
import com.aloe_droid.domain.entity.StoreQueryDistance
import com.aloe_droid.domain.entity.StoreQuerySortType
import com.aloe_droid.domain.entity.User
import com.aloe_droid.domain.repository.LocationRepository
import com.aloe_droid.domain.repository.StoreRepository
import com.aloe_droid.domain.repository.UserStoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetMapInfoUseCase @Inject constructor(
    private val userStoreRepository: UserStoreRepository,
    private val locationRepository: LocationRepository,
    private val storeRepository: StoreRepository
) {
    private val sortedDistances: List<StoreQueryDistance> = StoreQueryDistance.entries
        .filter { it != StoreQueryDistance.NONE }
        .sortedBy { it.maxKm }

    operator fun invoke(route: String, isLocalLocation: Boolean): Flow<StoreMapEntity> {
        val userFlow: Flow<User> = userStoreRepository.getUser().filterNotNull()
        val locationFlow: Flow<Location> = if (isLocalLocation) {
            locationRepository.getLocalLocation()
        } else {
            locationRepository.getLocation()
        }

        return combine(userFlow, locationFlow) { user: User, location: Location ->
            StoreQuery(
                userId = user.id,
                location = location,
                category = StoreQueryCategory.NONE,
                sortType = StoreQuerySortType.DISTANCE,
                distanceRange = StoreQueryDistance.K_5,
                searchQuery = "",
                onlyFavorites = false,
                page = 0,
                size = Integer.MAX_VALUE - 1
            )
        }.flatMapLatest { storeQuery ->
            storeRepository.getStoreList(storeQuery = storeQuery, requestRoute = route)
                .map { storeList: List<Store> -> storeQuery.location to storeList }
        }.map { (location: Location, storeList: List<Store>) ->
            StoreMapEntity(location = location, stores = storeList)
        }
    }

    operator fun invoke(
        route: String,
        isLocalLocation: Boolean,
        distance: Float,
        latitude: Double,
        longitude: Double
    ): Flow<StoreMapEntity> {
        val maxDistance: StoreQueryDistance = findClosestMaxDistance(inputKm = distance)
        val userFlow: Flow<User> = userStoreRepository.getUser().filterNotNull()
        val locationFlow: Flow<Location> = if (isLocalLocation) {
            locationRepository.getLocalLocation()
        } else {
            locationRepository.getLocation()
        }

        return userFlow.map { user: User ->
            StoreQuery(
                userId = user.id,
                location = Location(latitude = latitude, longitude = longitude),
                category = StoreQueryCategory.NONE,
                sortType = StoreQuerySortType.DISTANCE,
                distanceRange = maxDistance,
                searchQuery = "",
                onlyFavorites = false,
                page = 0,
                size = Integer.MAX_VALUE - 1
            )
        }.flatMapLatest { storeQuery ->
            storeRepository.getStoreList(storeQuery = storeQuery, requestRoute = route)
        }.combine(locationFlow) { storeList: List<Store>, location: Location ->
            StoreMapEntity(location = location, stores = storeList)
        }
    }

    private fun findClosestMaxDistance(inputKm: Float): StoreQueryDistance {
        return sortedDistances.firstOrNull { it.maxKm >= inputKm } ?: StoreQueryDistance.NONE
    }
}
