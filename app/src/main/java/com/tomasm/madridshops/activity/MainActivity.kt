package com.tomasm.madridshops.activity

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tomasm.madridshops.Manifest
import com.tomasm.madridshops.Navigator

import kotlinx.android.synthetic.main.activity_main.*
import com.tomasm.madridshops.R
import com.tomasm.madridshops.fragment.ListFragment
import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.interactor.getAllShops.GetAllShopsInteractor
import madridshops.tomasm.com.domain.interactor.getAllShops.GetAllShopsInteractorImplementation
import madridshops.tomasm.com.domain.model.Shops

class MainActivity : AppCompatActivity() {

    var listFragment: ListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Log.d( "App", "OnCreate MainActivity")

        setupMap()
        listFragment = supportFragmentManager.findFragmentById(R.id.list_fragment) as ListFragment

    }

    fun setupMap() {
        val getAllShopsInteractor: GetAllShopsInteractor = GetAllShopsInteractorImplementation(this)
        getAllShopsInteractor.execute(success = object: SuccessCompletion<Shops> {
            override fun successCompletion(shops: Shops) {
                initializeMap(shops)
            }

        },error = object: ErrorCompletion {

            override fun errorCompletion(errorMessage: String) {
                //baseContext porque estoy en medio de una closure
                Toast.makeText(baseContext, getString(R.string.loading_shops_error), Toast.LENGTH_LONG).show()
            }

        })


    }

    private fun initializeMap(shops: Shops) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync({ mapa: GoogleMap? ->
            Log.d("SUCCESS", "Habemus MAPA")
            centerMapInPosition(mapa!!, 40.416775, -3.703790)
            mapa!!.uiSettings.isRotateGesturesEnabled = false
            mapa!!.uiSettings.isZoomControlsEnabled = true
            showUserPosition(baseContext, mapa!!) //baseContext porque estamos dentro de una clausura y no tenemos acceso al context como this
            map = mapa
            addAllShopPins(shops)
        })
    }

    fun centerMapInPosition(map: GoogleMap, latitude: Double, longitude: Double) {
        val centerPoint = LatLng(latitude, longitude)
        val cameraPosition = CameraPosition.Builder().
                target(centerPoint).
                zoom(13f).
                build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun showUserPosition(context: Context, map: GoogleMap) {
        //Necesitamos pedirle permiso al usuario para saber su location
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
                    10)
            //TODO :Mostrar mensaje al usuario de que necesitamos los permisos para mostrar su ubicación
            return
        }
    }

    private var map: GoogleMap? = null

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10) {
            try {
                map?.isMyLocationEnabled = true
            } catch (e: SecurityException) {
                //Mostrar error de que no dieron el permiso
            }
        }
    }

    fun addAllShopPins(shops: Shops) {
        for (i in 0 until shops.count()) {
            val shop = shops.get(i)
            //TODO : Agregar al modelo latitud y longitud para mapearlo desde el ShopEntity
            addPin(map!!, 40.416775, -3.703790, shop.name)
        }
    }

    fun addPin(map: GoogleMap, latitude: Double, longitude: Double, title: String) {
        map.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title(title))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Navigator().navigateFromMainActivityToPicassoActivity(this)
        return true
    }
}
