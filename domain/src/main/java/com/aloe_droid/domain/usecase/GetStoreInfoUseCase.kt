package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.Location
import com.aloe_droid.domain.entity.Menu
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.entity.StoreDetail
import com.aloe_droid.domain.entity.StoreEntity
import com.aloe_droid.domain.entity.User
import com.aloe_droid.domain.repository.LocationRepository
import com.aloe_droid.domain.repository.StoreRepository
import com.aloe_droid.domain.repository.UserRepository
import com.aloe_droid.domain.repository.UserStoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import java.util.UUID
import javax.inject.Inject

class GetStoreInfoUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val userStoreRepository: UserStoreRepository,
    private val userRepository: UserRepository,
    private val storeRepository: StoreRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(storeId: UUID): Flow<StoreEntity> {
        val locationFlow: Flow<Location> = locationRepository.getLocalLocation()
        val userFlow: Flow<User> = userStoreRepository.getUser().filterNotNull()

        return combine(locationFlow, userFlow) { location: Location, user: User ->
            location to user
        }.flatMapLatest { (location, user) ->
            val userId: UUID = user.id
            val lat: Double = location.latitude
            val log: Double = location.longitude

            val storeFlow = storeRepository.getStore(id = storeId, latitude = lat, longitude = log)
            val storeDetailFlow = storeRepository.getStoreDetail(id = storeId)
            val storeMenuListFlow = storeRepository.getStoreMenus(id = storeId)
            val isLikeStore = userRepository.getStoreLike(userId = userId, storeId = storeId)
            combine(
                storeFlow,
                storeDetailFlow,
                storeMenuListFlow,
                isLikeStore
            ) { store: Store, storeDetail: StoreDetail, menuList: List<Menu>, isLike: Boolean ->
                StoreEntity(
                    store = store,
                    storeDetail = storeDetail,
                    menuList = menuList,
                    isLike = isLike
                )
            }
        }
    }
}
