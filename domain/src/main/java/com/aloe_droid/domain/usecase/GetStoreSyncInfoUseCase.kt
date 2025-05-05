package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.StoreSyncEntity
import com.aloe_droid.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.Instant
import javax.inject.Inject

class GetStoreSyncInfoUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {

    operator fun invoke(): Flow<StoreSyncEntity> = with(storeRepository) {
        combine(getStoreCount(), getStoreSyncTime()) { count: Long, time: Instant ->
            StoreSyncEntity(storeCount = count, syncTime = time)
        }
    }
}
