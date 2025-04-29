package com.aloe_droid.data.datasource.network.api

import com.aloe_droid.data.datasource.dto.banner.BannerDTO
import retrofit2.Response
import retrofit2.http.GET

interface BannerAPI {

    @GET("api/v1/banners")
    suspend fun getBannerList(): Response<List<BannerDTO>>
}
