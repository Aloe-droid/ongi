package com.aloe_droid.data.datasource.network.source

import com.aloe_droid.data.datasource.dto.banner.BannerDTO
import kotlinx.coroutines.flow.Flow

interface BannerDataSource {

    fun getBannerList(): Flow<List<BannerDTO>>
}
