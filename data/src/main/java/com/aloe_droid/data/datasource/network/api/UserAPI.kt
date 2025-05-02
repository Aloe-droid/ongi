package com.aloe_droid.data.datasource.network.api

import com.aloe_droid.data.datasource.dto.user.AddressDTO
import com.aloe_droid.data.datasource.dto.user.IdDTO
import com.aloe_droid.data.datasource.dto.user.LikeDTO
import com.aloe_droid.data.datasource.dto.user.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface UserAPI {

    @POST("api/v1/users")
    suspend fun findOrCreateUser(@Body addressDTO: AddressDTO): Response<UserDTO>

    @DELETE("api/v1/users/{id}")
    suspend fun deleteUser(@Path(value = "id") idDTO: IdDTO): Response<Boolean>

    @GET("api/v1/users/{userId}/favorites/{storeId}")
    suspend fun getStoreLike(
        @Path(value = "userId") userId: UUID,
        @Path(value = "storeId") storeId: UUID,
    ): Response<LikeDTO>

    @POST("api/v1/users/{userId}/favorites/{storeId}")
    suspend fun toggleStoreLike(
        @Path(value = "userId") userId: UUID,
        @Path(value = "storeId") storeId: UUID,
    ): Response<LikeDTO>

}
