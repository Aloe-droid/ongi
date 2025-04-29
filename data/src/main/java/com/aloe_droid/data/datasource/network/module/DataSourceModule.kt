package com.aloe_droid.data.datasource.network.module

import com.aloe_droid.data.datasource.network.source.BannerDataSource
import com.aloe_droid.data.datasource.network.source.BannerDataSourceImpl
import com.aloe_droid.data.datasource.network.source.StoreDataSource
import com.aloe_droid.data.datasource.network.source.StoreDataSourceImpl
import com.aloe_droid.data.datasource.network.source.UserDataSource
import com.aloe_droid.data.datasource.network.source.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource

    @Singleton
    @Binds
    abstract fun bindBannerDataSource(bannerDataSourceImpl: BannerDataSourceImpl): BannerDataSource

    @Singleton
    @Binds
    abstract fun bindStoreDataSource(storeDataSourceImpl: StoreDataSourceImpl): StoreDataSource
}
