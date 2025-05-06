package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.Location
import com.aloe_droid.domain.entity.StoreQuery
import com.aloe_droid.domain.entity.StoreQueryCategory
import com.aloe_droid.domain.entity.StoreQueryDistance
import com.aloe_droid.domain.entity.StoreQuerySortType
import com.aloe_droid.domain.entity.User
import com.aloe_droid.domain.repository.LocationRepository
import com.aloe_droid.domain.repository.StoreRepository
import com.aloe_droid.domain.repository.UserStoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredStoreUseCase @Inject constructor(
    private val userStoreRepository: UserStoreRepository,
    private val locationRepository: LocationRepository,
    private val storeRepository: StoreRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        category: StoreQueryCategory,
        sortType: StoreQuerySortType,
        distanceRange: StoreQueryDistance,
        searchQuery: String,
        onlyFavorites: Boolean
    ) = combine(
        userStoreRepository.getUser(),
        locationRepository.getLocalLocation()
    ) { user: User?, location: Location ->
        if (user == null) null
        else user to location
    }.filterNotNull()
        .map { (user: User, location: Location) ->
            StoreQuery(
                userId = user.id,
                location = location,
                searchQuery = searchQuery,
                category = category,
                sortType = sortType,
                distanceRange = distanceRange,
                onlyFavorites = onlyFavorites
            )
        }.flatMapLatest { storeQuery ->
            storeRepository.getStoreStream(storeQuery)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(searchQuery: String) = combine(
        userStoreRepository.getUser(),
        locationRepository.getLocalLocation()
    ) { user: User?, location: Location ->
        if (user == null) null
        else user to location
    }.filterNotNull()
        .map { (user: User, location: Location) ->
            StoreQuery(userId = user.id, location = location, searchQuery = searchQuery)
        }.flatMapLatest { storeQuery ->
            storeRepository.getStoreStream(storeQuery)
        }
}
