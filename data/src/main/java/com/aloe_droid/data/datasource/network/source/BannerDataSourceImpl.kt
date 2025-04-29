package com.aloe_droid.data.datasource.network.source

import com.aloe_droid.data.datasource.dto.banner.BannerDTO
import com.aloe_droid.data.datasource.network.api.BannerAPI
import com.aloe_droid.data.datasource.network.util.ApiExt.Companion.safeApiCallToFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BannerDataSourceImpl @Inject constructor(private val bannerAPI: BannerAPI) : BannerDataSource {

    override fun getBannerList(): Flow<List<BannerDTO>> = safeApiCallToFlow {
        bannerAPI.getBannerList()
    }
}
