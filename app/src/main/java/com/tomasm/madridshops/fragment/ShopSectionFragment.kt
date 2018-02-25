package com.tomasm.madridshops.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.tomasm.madridshops.Navigator
import com.tomasm.madridshops.R
import com.tomasm.madridshops.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_section_shop.*
import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.interactor.getAll.GetAllInteractor
import madridshops.tomasm.com.domain.interactor.getAll.GetAllShopsInteractorImplementation
import madridshops.tomasm.com.domain.model.Shop
import madridshops.tomasm.com.domain.model.Shops


class ShopSectionFragment : BaseSectionFragment(), ShopListFragment.OnItemSelectedListener {

    private var map: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_section_shop, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
    }

    private fun setup() {

        val getAllShopsInteractor: GetAllInteractor<Shops> = GetAllShopsInteractorImplementation(activity.baseContext)
        getAllShopsInteractor.execute(success = object : SuccessCompletion<Shops> {
            override fun successCompletion(shops: Shops) {

                initializeMap(shops)
                loadListFragment(shops)
            }

        }, error = object : ErrorCompletion {

            override fun errorCompletion(errorMessage: String) {
                //baseContext porque estoy en medio de una closure
                Toast.makeText(activity.baseContext, getString(R.string.loading_shops_error), Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun initializeMap(shops: Shops) {

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_shop_fragment) as MapFragment
        mapFragment.getMapAsync({ map ->
            setUpMap(map)
            this.map = map
            addAllShopPins(shops)
        })
    }

    fun loadListFragment(shops: Shops) {

        val listFragment = ShopListFragment.newInstance(shops)
        listFragment.setItemListener(this)
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.list_shop_fragment, listFragment)
        transaction.commit()
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

    private fun addAllShopPins(shops: Shops) {
        for (i in 0 until shops.count()) {
            val shop = shops.get(i)

            addPin(map!!, shop)
        }
    }

    private fun addPin(map: GoogleMap, shop: Shop) {
        val marker = map.addMarker(MarkerOptions().position(LatLng(shop.lat, shop.lon)).title(shop.name))
        marker.tag = shop
        map.setOnInfoWindowClickListener(this)
    }

    //Delegate method from InfoWindowListener
    override fun onInfoWindowClick(marker: Marker?) {
        val selectedShop = marker!!.tag as Shop
        Navigator().navigateFromMainActivityToDetailActivity(activity as MainActivity, selectedShop)
        Log.d("MarkerClick", "Click on marker shop: " + selectedShop.name)
    }


    override fun onItemSelected(shop: Shop, position: Int) {
        Navigator().navigateFromMainActivityToDetailActivity(activity as MainActivity, shop)
    }
}