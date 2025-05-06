package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.HomeEntity
import com.aloe_droid.domain.entity.Location
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.entity.StoreQuery
import com.aloe_droid.domain.entity.StoreQuerySortType
import com.aloe_droid.domain.entity.User
import com.aloe_droid.domain.repository.BannerRepository
import com.aloe_droid.domain.repository.LocationRepository
import com.aloe_droid.domain.repository.StoreRepository
import com.aloe_droid.domain.repository.UserStoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import java.util.UUID
import javax.inject.Inject

class GetHomeInfoUseCase @Inject constructor(
    private val userStoreRepository: UserStoreRepository,
    private val locationRepository: LocationRepository,
    private val bannerRepository: BannerRepository,
    private val storeRepository: StoreRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<HomeEntity> = combine(
        userStoreRepository.getUser(),
        locationRepository.getLocation()
    ) { user: User?, location: Location ->
        if (user == null) null
        else user to location
    }.filterNotNull()
        .flatMapLatest { (user: User, location: Location) ->
            val bannerListFlow = bannerRepository.getBannerList()
            val favoriteStoreListFlow = getFavoriteStoreList(location = location, userId = user.id)
            val nearbyStoreListFlow = getNearbyStoreList(location = location, userId = user.id)

            combine(
                bannerListFlow,
                favoriteStoreListFlow,
                nearbyStoreListFlow
            ) { bannerList, favoritesStoreList, nearbyStoreList ->
                HomeEntity(
                    bannerList = bannerList,
                    favoriteStoreList = favoritesStoreList,
                    nearbyStoreList = nearbyStoreList,
                    location = location
                )
            }
        }

    private fun getNearbyStoreList(location: Location, userId: UUID): Flow<List<Store>> {
        val query = StoreQuery(
            userId = userId,
            location = Location(latitude = location.latitude, longitude = location.longitude),
            sortType = StoreQuerySortType.DISTANCE,
            size = HOME_STORE_SIZE
        )

        return storeRepository.getStoreList(query)
    }

    private fun getFavoriteStoreList(location: Location, userId: UUID): Flow<List<Store>> {
        val query = StoreQuery(
            userId = userId,
            location = Location(latitude = location.latitude, longitude = location.longitude),
            sortType = StoreQuerySortType.FAVORITE,
            size = HOME_STORE_SIZE
        )

        return storeRepository.getStoreList(query)
    }

    companion object {
        private const val HOME_STORE_SIZE: Int = 10
    }
}
