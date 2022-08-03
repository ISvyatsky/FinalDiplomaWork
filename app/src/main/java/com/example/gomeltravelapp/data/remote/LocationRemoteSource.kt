package com.example.gomeltravelapp.data.remote

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.example.gomeltravelapp.domain.model.LocationModel
import com.example.gomeltravelapp.domain.remote.LocationRemoteDataSource
import com.google.android.gms.location.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class LocationRemoteSource
@Inject
constructor(
    private val context: Context
) : LocationRemoteDataSource {

    override suspend fun getCurrentLocationLive(): Flow<LocationModel> {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        return fusedLocationProviderClient.getCurrentLocationAsFlow()
    }

    override suspend fun getCurrentLocationOnce(): LocationModel {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        return fusedLocationProviderClient.getCurrentLocationOnce()
    }

    private suspend fun FusedLocationProviderClient.getCurrentLocationAsFlow(): Flow<LocationModel> =
        callbackFlow {
            val locationRequest: LocationRequest = LocationRequest.create()
                .apply {
                    interval = 1000L
                    fastestInterval = 500L
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    p0 ?: return
                    for (location in p0.locations) {
                        this@callbackFlow.trySend(setLocationData(location)).isSuccess
                    }
                }
            }
            requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            awaitClose {
                removeLocationUpdates(locationCallback)
            }
        }

    private suspend fun FusedLocationProviderClient.getCurrentLocationOnce(): LocationModel =
        suspendCancellableCoroutine { continuation ->
            val locationRequest: LocationRequest = LocationRequest.create()
                .apply {
                    interval = 1000L
                    fastestInterval = 500L
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    p0 ?: return
                    for (location in p0.locations) {
                        removeLocationUpdates(this)
                        continuation.resume(setLocationData(location))
                    }
                }
            }
            requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            continuation.invokeOnCancellation {
                removeLocationUpdates(locationCallback)
            }
        }

    private fun setLocationData(location: Location) =
        LocationModel(
            long = location.longitude,
            lat = location.latitude
        )
}
