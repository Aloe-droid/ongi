package com.aloe_droid.data.datasource.manager.source

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.aloe_droid.domain.exception.LocationPermissionException
import com.aloe_droid.domain.exception.LocationUnavailableException
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Tasks
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationDataSource {

    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
    override fun getLocation(): Flow<Location> = flow {
        val hasPermission: Boolean = listOf(
            ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION),
            ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
        ).any { it == PackageManager.PERMISSION_GRANTED }

        checkGPS()
        if (!hasPermission) throw LocationPermissionException()

        val location: Location = getLastLocation()
            ?: getCurrentLocation()
            ?: throw LocationUnavailableException()
        emit(location)
    }

    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
    private fun getLastLocation(): Location? {
        val locationTask = fusedLocationProviderClient.lastLocation
        return Tasks.await(locationTask)
    }

    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
    private fun getCurrentLocation(): Location? {
        val request: CurrentLocationRequest = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setMaxUpdateAgeMillis(MAX_UPDATE_AGE)
            .setDurationMillis(DURATION_TIME)
            .build()

        val locationTask = fusedLocationProviderClient.getCurrentLocation(request, null)
        return Tasks.await(locationTask)
    }

    private suspend fun checkGPS(): LocationSettingsResponse {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, Long.MAX_VALUE)
            .build()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(context)
        return settingsClient.checkLocationSettings(builder.build())
            .await()
    }

    companion object {
        const val MAX_UPDATE_AGE: Long = 0L
        const val DURATION_TIME: Long = 10_000L
    }
}
