package com.aloe_droid.domain.repository

import com.aloe_droid.domain.entity.Banner
import kotlinx.coroutines.flow.Flow

interface BannerRepository {

    fun getBannerList(): Flow<List<Banner>>
}
