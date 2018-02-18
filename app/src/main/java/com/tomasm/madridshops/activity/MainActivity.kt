package com.tomasm.madridshops.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.squareup.picasso.Picasso
import com.tomasm.madridshops.Navigator

import kotlinx.android.synthetic.main.activity_main.*
import com.tomasm.madridshops.R
import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.interactor.getAllShops.GetAllShopsInteractor
import madridshops.tomasm.com.domain.interactor.getAllShops.GetAllShopsInteractorImplementation
import madridshops.tomasm.com.domain.model.Shop
import madridshops.tomasm.com.domain.model.Shops
import com.tomasm.madridshops.common.Map
import com.tomasm.madridshops.fragment.ShopListFragment


class MainActivity : AppCompatActivity(), GoogleMap.OnInfoWindowClickListener, ShopListFragment.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Picasso setup
        Picasso.with(this).setIndicatorsEnabled(false) //Muestra un indicador rojo cuando la imagen no estaba en cache y verde cuando lee de cache
        Picasso.with(this).isLoggingEnabled = true  //Logea lo que Picasso va haciendo

        setupMap()
    }

    fun setupMap() {
        val getAllShopsInteractor: GetAllShopsInteractor = GetAllShopsInteractorImplementation(this)
        getAllShopsInteractor.execute(success = object: SuccessCompletion<Shops> {
            override fun successCompletion(shops: Shops) {

                initializeMap(shops)
                loadListFragment(shops)
            }

        },error = object: ErrorCompletion {

            override fun errorCompletion(errorMessage: String) {
                //baseContext porque estoy en medio de una closure
                Toast.makeText(baseContext, getString(R.string.loading_shops_error), Toast.LENGTH_LONG).show()
            }

        })


    }

    fun loadListFragment(shops: Shops) {

        if (findViewById<View>(R.id.list_fragment) != null) {
            //Comprobar primero que no fue añadido previamente porque sino se va a añadir cada vez que la actividad se recargue
            if (fragmentManager.findFragmentById(R.id.list_fragment) == null) {
                //Añadir fragment en Activity
                val fragment = ShopListFragment.newInstance(shops)
                fragmentManager.beginTransaction()
                        .add(R.id.list_fragment, fragment)
                        .commit()
            }
        }
    }

    private var map: GoogleMap? = null

    private fun initializeMap(shops: Shops) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync({ mapa: GoogleMap? ->
            Log.d("SUCCESS", "Habemus MAPA")
            Map().centerMapInPosition(mapa!!, 40.416775, -3.703790, 13f)
            mapa!!.uiSettings.isRotateGesturesEnabled = false
            mapa!!.uiSettings.isZoomControlsEnabled = true
            Map().showUserPosition(baseContext, mapa!!, this) //baseContext porque estamos dentro de una clausura y no tenemos acceso al context como this
            map = mapa
            addAllShopPins(shops)
        })
    }


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

            addPin(map!!, shop)
        }
    }

    fun addPin(map: GoogleMap, shop: Shop) {
        val marker = map.addMarker(MarkerOptions().position(LatLng(shop.lat, shop.lon)).title(shop.name))
        marker.tag = shop
        map.setOnInfoWindowClickListener(this)
    }

    //Delegate method from InfoWindowListener
    override fun onInfoWindowClick(marker: Marker?) {
        val selectedShop = marker!!.tag as Shop
        Navigator().navigateFromMainActivityToDetailActivity(this, selectedShop)
        Log.d("MarkerClick", "Click on marker shop: " + selectedShop.name)
    }

    //ItemSelected from Fragmentlist
    override fun onItemSelected(item: Shop, position: Int) {
        Navigator().navigateFromMainActivityToDetailActivity(this,item)
    }
}
