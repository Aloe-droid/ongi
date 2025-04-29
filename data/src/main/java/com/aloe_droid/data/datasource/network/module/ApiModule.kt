package com.aloe_droid.data.datasource.network.module

import com.aloe_droid.data.datasource.network.api.BannerAPI
import com.aloe_droid.data.datasource.network.api.StoreAPI
import com.aloe_droid.data.datasource.network.api.UserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesUserAPI(retrofit: Retrofit): UserAPI = retrofit.create()

    @Singleton
    @Provides
    fun providesBannerAPI(retrofit: Retrofit): BannerAPI = retrofit.create()

    @Singleton
    @Provides
    fun providesStoreAPI(retrofit: Retrofit): StoreAPI = retrofit.create()
}
