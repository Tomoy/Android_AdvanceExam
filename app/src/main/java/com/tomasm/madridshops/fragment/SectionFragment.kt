package com.tomasm.madridshops.fragment
import android.os.Bundle
import android.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
import com.tomasm.madridshops.common.Map
import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.interactor.getAllShops.GetAllShopsInteractor
import madridshops.tomasm.com.domain.interactor.getAllShops.GetAllShopsInteractorImplementation
import madridshops.tomasm.com.domain.model.Section
import madridshops.tomasm.com.domain.model.Shop
import madridshops.tomasm.com.domain.model.Shops


class SectionFragment : Fragment(), GoogleMap.OnInfoWindowClickListener, ShopListFragment.OnItemSelectedListener {

    companion object {
        private val ACTUAL_SECTION = "ACTUAL_SECTION"

        //Método factory de instancia
        fun newInstance(initialSection: Section): SectionFragment {
            val fragment = SectionFragment()

            val arguments = Bundle()
            arguments.putSerializable(ACTUAL_SECTION, initialSection)
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        if (inflater != null) {
            rootView = inflater.inflate(R.layout.fragment_section, container, false)
            setupMap()
        }

        return rootView
    }

    fun setupMap() {

        val actualSection = arguments.getSerializable(ACTUAL_SECTION) as Section

        if (actualSection.name == "Shops") {

            val getAllShopsInteractor: GetAllShopsInteractor = GetAllShopsInteractorImplementation(activity.baseContext)
            getAllShopsInteractor.execute(success = object: SuccessCompletion<Shops> {
                override fun successCompletion(shops: Shops) {

                    initializeMap(shops)
                    loadListFragment(shops)
                }

            },error = object: ErrorCompletion {

                override fun errorCompletion(errorMessage: String) {
                    //baseContext porque estoy en medio de una closure
                    Toast.makeText(activity.baseContext, getString(R.string.loading_shops_error), Toast.LENGTH_LONG).show()
                }

            })
        } else {

            val fakeActivities = Shops(mutableListOf<Shop>())

            initializeMap(fakeActivities)
            loadListFragment(fakeActivities)
        }
    }

    fun loadListFragment(shops: Shops) {

        val listFragment = ShopListFragment.newInstance(shops)
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.list_fragment, listFragment)
        transaction.commit()
    }

    private var map: GoogleMap? = null

    private fun initializeMap(shops: Shops) {

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getMapAsync({ mapa: GoogleMap? ->
            Log.d("SUCCESS", "Habemus MAPA")
            Map().centerMapInPosition(mapa!!, 40.416775, -3.703790, 13f)
            mapa!!.uiSettings.isRotateGesturesEnabled = false
            mapa!!.uiSettings.isZoomControlsEnabled = true
            Map().showUserPosition(activity.baseContext, mapa!!, activity) //baseContext porque estamos dentro de una clausura y no tenemos acceso al context como this
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
        Navigator().navigateFromMainActivityToDetailActivity(activity as MainActivity, selectedShop)
        Log.d("MarkerClick", "Click on marker shop: " + selectedShop.name)
    }

    //ItemSelected from Fragmentlist
    override fun onItemSelected(item: Shop, position: Int) {
        Navigator().navigateFromMainActivityToDetailActivity(activity as MainActivity,item)
    }
}