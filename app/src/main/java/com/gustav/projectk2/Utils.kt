package com.gustav.projectk2

import android.location.Location
import java.math.RoundingMode
import java.text.DecimalFormat

class Utils {

    companion object {
        fun getFormattedLocationInDegree(location: Location?): String? {
            if(location != null) {
                val longitude = location.longitude
                val latitude = location.latitude
                var latSeconds = Math.round(latitude * 3600).toDouble()
                val latDegrees = latSeconds.toInt() / 3600
                latSeconds = Math.abs(latSeconds % 3600)
                val latMinutes = latSeconds / 60
                val dflat = DecimalFormat("00.00")
                dflat.roundingMode = RoundingMode.HALF_UP
                val latitudeMin = dflat.format(latMinutes)
                var longSeconds = Math.round(longitude * 3600).toDouble()
                val longDegrees = longSeconds.toInt() / 3600
                longSeconds = Math.abs(longSeconds % 3600)
                val longMinutes = longSeconds / 60
                val dflon = DecimalFormat("00.00")
                dflon.roundingMode = RoundingMode.HALF_UP
                val longitudeMin = dflon.format(longMinutes)
                val latDegree = if (latDegrees >= 0) "N" else "S"
                val lonDegrees = if (longDegrees >= 0) "E" else "W"
                val dfLatDeg = DecimalFormat("00")
                val dfLonDeg = DecimalFormat("000")
                val formattedLatDegrees = dfLatDeg.format(Math.abs(latDegrees).toLong())
                val formattedLongDegrees = dfLonDeg.format(Math.abs(longDegrees).toLong())
                return (latDegree + " " + formattedLatDegrees + "°" + latitudeMin + "'"
                        + " " + lonDegrees + " " + formattedLongDegrees + "°" + longitudeMin
                        + "'")
            } else return "location null"
        }

    }
}