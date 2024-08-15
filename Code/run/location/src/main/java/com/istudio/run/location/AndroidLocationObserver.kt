package com.istudio.run.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.istudio.core.domain.location.LocationWithAltitude
import com.istudio.run.domain.LocationObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/*
 * Once we set this class, When ever there is a location change the callback will be triggered
 * We convert the callback to flow and give it back.
 */
internal class AndroidLocationObserver(
    private val context: Context
) : LocationObserver {

    // Fused location service is the android dependency that android offers to track users location
    private val client = LocationServices.getFusedLocationProviderClient(context)

    override fun observeLocation(interval: Long): Flow<LocationWithAltitude> {
        return callbackFlow {

            val locationManager = context.getSystemService<LocationManager>()
            var isGpsEnabled = false
            var isNetworkEnabled = false

            // It is best to have both GPS and Network enabled !
            while(!isGpsEnabled && !isNetworkEnabled) {
                locationManager?.let {
                    // Using the location manager -> We can check if the location and the network is enabled
                    isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                    if(!isGpsEnabled && !isNetworkEnabled) {
                        // While both are not enabled, Wait for some time so they could get enabled
                        delay(3000L)
                    }
                }
            }

            if (
                ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Close the flow
                close()
            } else {
                // Location is available: This is the cached location. Google also tracks location from other apps also so that location is immediately available
                client.lastLocation.addOnSuccessListener {
                    it?.let { location ->
                        // Here we map into the domain model and then send it.
                        trySend(location.toLocationWithAltitude())
                    }
                }

                // <-------------- Also try to get the new fresh location again ------------------->
                // Looper object
                val looperMain = Looper.getMainLooper()
                // Location-Request
                val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, interval).build()
                // Location-Callback
                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        super.onLocationResult(result)
                        result.locations.lastOrNull()?.let { location ->
                            trySend(location.toLocationWithAltitude())
                        }
                    }
                }
                // Perform the request
                client.requestLocationUpdates(request, locationCallback, looperMain)

                // We keep getting location until the flow of the class is active, then it closes on its own
                // Example: Say a view model is listening the updates, These updates will keep happen until the viewmodel is active.

                awaitClose {
                    // When closing the flow remove the location callback!
                    client.removeLocationUpdates(locationCallback)
                }
            }
        }
    }
}