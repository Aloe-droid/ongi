package com.aloe_droid.data.datasource.datastore.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.aloe_droid.data.datasource.dto.LocationDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationDatastoreImpl @Inject constructor(
    @ApplicationContext val context: Context
) : LocationDatastore {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = LOCATION)

    override suspend fun saveLocation(locationDTO: LocationDTO) {
        context.datastore.edit { preferences: MutablePreferences ->
            val latKey: Preferences.Key<Double> = doublePreferencesKey(LATITUDE)
            val logKey: Preferences.Key<Double> = doublePreferencesKey(LONGITUDE)
            preferences[latKey] = locationDTO.latitude
            preferences[logKey] = locationDTO.longitude
        }
    }

    override fun getLocation(): Flow<LocationDTO?> = context.datastore.data
        .map { preferences: Preferences ->
            val latKey: Preferences.Key<Double> = doublePreferencesKey(LATITUDE)
            val logKey: Preferences.Key<Double> = doublePreferencesKey(LONGITUDE)
            val latitude = preferences[latKey]
            val longitude = preferences[logKey]
            if (latitude != null && longitude != null) {
                LocationDTO(latitude = latitude, longitude = longitude)
            } else {
                null
            }
        }

    override suspend fun clearLocation() {
        val latKey: Preferences.Key<Double> = doublePreferencesKey(LATITUDE)
        val logKey: Preferences.Key<Double> = doublePreferencesKey(LONGITUDE)
        context.datastore.edit { preferences: MutablePreferences ->
            preferences.remove(key = latKey)
            preferences.remove(key = logKey)
        }
    }

    companion object {
        private const val LOCATION = "location"
        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"
    }
}
