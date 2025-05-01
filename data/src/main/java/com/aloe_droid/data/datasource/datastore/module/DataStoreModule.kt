package com.aloe_droid.data.datasource.datastore.module

import com.aloe_droid.data.datasource.datastore.source.LocationDatastore
import com.aloe_droid.data.datasource.datastore.source.LocationDatastoreImpl
import com.aloe_droid.data.datasource.datastore.source.UserDatastore
import com.aloe_droid.data.datasource.datastore.source.UserDatastoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Singleton
    @Binds
    abstract fun bindsUserDatastore(userDatastoreImpl: UserDatastoreImpl): UserDatastore

    @Singleton
    @Binds
    abstract fun bindsLocationDatastore(locationDatastoreImpl: LocationDatastoreImpl): LocationDatastore
}
