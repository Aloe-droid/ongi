package com.aloe_droid.data.datasource.network.api

import com.aloe_droid.data.datasource.dto.store.StorePage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface StoreAPI {

    @GET("api/v1/stores")
    suspend fun getStores(@QueryMap queryParams: Map<String, String>): Response<StorePage>

}
