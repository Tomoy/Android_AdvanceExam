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
import madridshops.tomasm.com.domain.model.Item
import madridshops.tomasm.com.domain.model.Shop

class DetailActivity : AppCompatActivity() {

    private var map: GoogleMap? = null

    companion object {

        val SELECTED_ITEM = "SelectedItem"

        fun intent(context: Context, item:Item) : Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(SELECTED_ITEM, item)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val selectedItem = intent.getSerializableExtra(SELECTED_ITEM) as Item

        title = selectedItem.name

        setupMap(selectedItem)
        fillShopDetails(selectedItem)
    }

    fun setupMap(item: Item) {

        val mapFragment = supportFragmentManager.findFragmentById(R.id.detail_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync({ mapa: GoogleMap? ->
            Log.d("SUCCESS", "Mapa del detalle inicializado")
            Map().centerMapInPosition(mapa!!, item.lat, item.lon, 20f)
            mapa!!.uiSettings.isRotateGesturesEnabled = false
            mapa!!.uiSettings.isZoomControlsEnabled = true
            Map().showUserPosition(baseContext, mapa!!, this) //baseContext porque estamos dentro de una clausura y no tenemos acceso al context como this
            map = mapa
            addShopPin(item)
        })
    }

    fun addShopPin(item: Item){
        val marker = map!!.addMarker(MarkerOptions().position(LatLng(item.lat, item.lon)).title(item.name))

    }

    fun fillShopDetails(item: Item){

        item_name_textView.text = item.name
        item_description_textView.text = item.description
        item_address_textView.text = item.address
    }

}
