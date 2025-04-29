package com.aloe_droid.data.datasource.manager.module

import android.content.Context
import com.aloe_droid.data.datasource.manager.source.LocationDataSource
import com.aloe_droid.data.datasource.manager.source.LocationDataSourceImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationManagerModule {

    @Provides
    @Singleton
    fun providesLocationManager(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsLocationDataSource(locationDataSourceImpl: LocationDataSourceImpl): LocationDataSource
}