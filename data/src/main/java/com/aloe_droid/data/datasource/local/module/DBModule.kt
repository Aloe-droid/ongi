package com.aloe_droid.data.datasource.local.module

import android.content.Context
import androidx.room.Room
import com.aloe_droid.data.datasource.local.dao.SearchHistoryDao
import com.aloe_droid.data.datasource.local.dao.StoreDao
import com.aloe_droid.data.datasource.local.dao.StoreKeyDao
import com.aloe_droid.data.datasource.local.database.OnGiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ): OnGiDatabase = Room
        .databaseBuilder(context = context, klass = OnGiDatabase::class.java, name = "ongi.db")
        .build()

    @Singleton
    @Provides
    fun providesStoreDao(onGiDatabase: OnGiDatabase): StoreDao = onGiDatabase.storeDao()

    @Singleton
    @Provides
    fun providesStoreKeyDao(onGiDatabase: OnGiDatabase): StoreKeyDao = onGiDatabase.storeKeyDao()

    @Singleton
    @Provides
    fun providesSearchHistoryDao(onGiDatabase: OnGiDatabase): SearchHistoryDao =
        onGiDatabase.searchHistoryDao()

}
