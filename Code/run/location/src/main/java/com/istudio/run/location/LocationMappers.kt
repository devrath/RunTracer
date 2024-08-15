package com.istudio.run.location

import android.location.Location
import com.istudio.core.domain.location.LocationWithAltitude

internal fun Location.toLocationWithAltitude(): LocationWithAltitude {
    // Location class provides the latitude, longitude and altitude => Now map them into our model class
    return LocationWithAltitude(
        location = com.istudio.core.domain.location.Location(
            latitude = latitude, longitude = longitude
        ), altitude = altitude)
}