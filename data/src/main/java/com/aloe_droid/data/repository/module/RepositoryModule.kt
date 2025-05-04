package com.aloe_droid.data.repository.module

import com.aloe_droid.data.repository.impl.BannerRepositoryImpl
import com.aloe_droid.data.repository.impl.LocationRepositoryImpl
import com.aloe_droid.data.repository.impl.SearchHistoryRepositoryImpl
import com.aloe_droid.data.repository.impl.StoreRepositoryImpl
import com.aloe_droid.data.repository.impl.UserRepositoryImpl
import com.aloe_droid.data.repository.impl.UserStoreRepositoryImpl
import com.aloe_droid.domain.repository.BannerRepository
import com.aloe_droid.domain.repository.LocationRepository
import com.aloe_droid.domain.repository.SearchHistoryRepository
import com.aloe_droid.domain.repository.StoreRepository
import com.aloe_droid.domain.repository.UserRepository
import com.aloe_droid.domain.repository.UserStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsUserStoreRepository(userStoreRepositoryImpl: UserStoreRepositoryImpl): UserStoreRepository

    @Singleton
    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindsBannerRepository(bannerRepositoryImpl: BannerRepositoryImpl): BannerRepository

    @Singleton
    @Binds
    abstract fun bindsLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository

    @Singleton
    @Binds
    abstract fun bindsStoreRepository(storeRepositoryImpl: StoreRepositoryImpl): StoreRepository

    @Singleton
    @Binds
    abstract fun bindsSearchHistoryRepository(searchHistoryRepositoryImpl: SearchHistoryRepositoryImpl): SearchHistoryRepository
}
