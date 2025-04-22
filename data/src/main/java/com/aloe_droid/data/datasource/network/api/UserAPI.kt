package com.aloe_droid.data.datasource.network.api

import com.aloe_droid.data.datasource.dto.user.AddressDTO
import com.aloe_droid.data.datasource.dto.user.IdDTO
import com.aloe_droid.data.datasource.dto.user.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {

    @POST("api/v1/users")
    suspend fun findOrCreateUser(@Body addressDTO: AddressDTO): Response<UserDTO>

    @DELETE("api/v1/users/{id}")
    suspend fun deleteUser(@Path(value = "id") idDTO: IdDTO): Response<Boolean>

}
