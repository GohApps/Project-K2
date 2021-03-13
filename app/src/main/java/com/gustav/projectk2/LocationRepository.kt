package com.gustav.projectk2

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import com.gustav.projectk2.database.NoteEvent

class LocationRepository(
    val context: Context,
    val locationManager: LocationManager
) : LifecycleObserver {

    var provider: String? = null
    lateinit var locationListener : LocationListener

    fun registerListener(listener: LocationListener){
        locationListener = listener
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        stopUpdate()
    }

    fun acquirePosition() {
        provider = locationManager.getBestProvider(Criteria(), false)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        locationManager.requestLocationUpdates(provider, 400, 1f, locationListener)
    }


    fun stopUpdate() {
        locationManager.removeUpdates(locationListener)
    }


}