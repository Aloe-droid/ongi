package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.Banner
import com.aloe_droid.domain.entity.HomeEntity
import com.aloe_droid.domain.entity.Location
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.entity.StoreQuery
import com.aloe_droid.domain.entity.StoreQuerySortType
import com.aloe_droid.domain.repository.BannerRepository
import com.aloe_droid.domain.repository.LocationRepository
import com.aloe_droid.domain.repository.StoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetHomeInfoUseCase @Inject constructor(
    private val bannerRepository: BannerRepository,
    private val locationRepository: LocationRepository,
    private val storeRepository: StoreRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<HomeEntity> {
        return locationRepository.getLocation().flatMapLatest { location: Location ->
            val bannerListFlow: Flow<List<Banner>> = bannerRepository.getBannerList()
            val favoriteStoreListFlow: Flow<List<Store>> = getFavoriteStoreList(location = location)
            val nearbyStoreListFlow: Flow<List<Store>> = getNearbyStoreList(location = location)

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
    }

    private fun getNearbyStoreList(location: Location): Flow<List<Store>> {
        val query = StoreQuery(
            location = Location(latitude = location.latitude, longitude = location.longitude),
            sortType = StoreQuerySortType.DISTANCE,
            size = HOME_STORE_SIZE
        )

        return storeRepository.getStoreList(query)
    }

    private fun getFavoriteStoreList(location: Location): Flow<List<Store>> {
        val query = StoreQuery(
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
