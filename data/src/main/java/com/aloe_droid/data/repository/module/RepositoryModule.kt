package com.aloe_droid.data.repository.module

import com.aloe_droid.data.repository.impl.UserRepositoryImpl
import com.aloe_droid.data.repository.impl.UserStoreRepositoryImpl
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

}
