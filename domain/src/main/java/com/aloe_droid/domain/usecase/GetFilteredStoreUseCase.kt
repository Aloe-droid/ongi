package com.aloe_droid.domain.usecase

import androidx.paging.PagingData
import com.aloe_droid.domain.entity.Location
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.entity.StoreQuery
import com.aloe_droid.domain.repository.LocationRepository
import com.aloe_droid.domain.repository.StoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredStoreUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val storeRepository: StoreRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(storeQuery: StoreQuery): Flow<PagingData<Store>> = locationRepository
        .getLocalLocation()
        .map { location: Location -> storeQuery.copy(location = location) }
        .flatMapLatest { storeRepository.getStoreStream(it) }


    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(searchQuery: String): Flow<PagingData<Store>> = locationRepository
        .getLocalLocation()
        .map { location: Location -> StoreQuery(location = location, searchQuery = searchQuery) }
        .flatMapLatest { storeRepository.getStoreStream(it) }

}
