package com.tomasm.madridshops.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng


class Map {

    fun centerMapInPosition(map: GoogleMap, latitude: Double, longitude: Double, zoom: Float) {
        val centerPoint = LatLng(latitude, longitude)
        val cameraPosition = CameraPosition.Builder().
                target(centerPoint).
                zoom(zoom).
                build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun showUserPosition(context: Context, map: GoogleMap, activity: Activity) {
        //Necesitamos pedirle permiso al usuario para saber su location
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    10)
            //TODO :Mostrar mensaje al usuario de que necesitamos los permisos para mostrar su ubicación
            return
        }
    }
}