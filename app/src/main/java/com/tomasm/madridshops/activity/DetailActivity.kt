package com.tomasm.madridshops.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tomasm.madridshops.R
import com.tomasm.madridshops.common.Map

import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import madridshops.tomasm.com.domain.model.Shop

class DetailActivity : AppCompatActivity() {

    private var map: GoogleMap? = null

    companion object {

        val SELECTED_SHOP = "SelectedShop"

        fun intent(context: Context, shop:Shop) : Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(SELECTED_SHOP, shop)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val selectedShop = intent.getSerializableExtra(SELECTED_SHOP) as Shop

        title = selectedShop.name

        setupMap(selectedShop)
        fillShopDetails(selectedShop)
    }

    fun setupMap(shop: Shop) {

        val mapFragment = supportFragmentManager.findFragmentById(R.id.detail_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync({ mapa: GoogleMap? ->
            Log.d("SUCCESS", "Mapa del detalle inicializado")
            Map().centerMapInPosition(mapa!!, shop.lat, shop.lon, 20f)
            mapa!!.uiSettings.isRotateGesturesEnabled = false
            mapa!!.uiSettings.isZoomControlsEnabled = true
            Map().showUserPosition(baseContext, mapa!!, this) //baseContext porque estamos dentro de una clausura y no tenemos acceso al context como this
            map = mapa
            addShopPin(shop)
        })
    }

    fun addShopPin(shop: Shop){
        val marker = map!!.addMarker(MarkerOptions().position(LatLng(shop.lat, shop.lon)).title(shop.name))

    }

    fun fillShopDetails(shop: Shop){

        shop_name_textView.text = shop.name
        shop_description_textView.text = shop.description
        shop_address_textView.text = shop.address
    }

}
