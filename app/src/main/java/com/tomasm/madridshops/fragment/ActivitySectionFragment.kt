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
import kotlinx.android.synthetic.main.fragment_section_activity.*
import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.interactor.getAll.GetAllActivitiesInteractorImplementation
import madridshops.tomasm.com.domain.interactor.getAll.GetAllInteractor
import madridshops.tomasm.com.domain.model.Activities
import madridshops.tomasm.com.domain.model.Activity

class ActivitySectionFragment : BaseSectionFragment(), ActivityListFragment.OnItemSelectedListener {

    private var map: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_section_activity, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
    }

    private fun setup() {
        val getAllActivitiesInteractor: GetAllInteractor<Activities> = GetAllActivitiesInteractorImplementation(activity.baseContext)
        getAllActivitiesInteractor.execute(success = object : SuccessCompletion<Activities> {
            override fun successCompletion(activities: Activities) {

                initializeMap(activities)
                loadListFragment(activities)
            }

        }, error = object : ErrorCompletion {

            override fun errorCompletion(errorMessage: String) {
                //baseContext porque estoy en medio de una closure
                Toast.makeText(activity.baseContext, getString(R.string.loading_activities_error), Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun initializeMap(activities: Activities) {

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_activity_fragment) as MapFragment
        mapFragment.getMapAsync({ map ->
            setUpMap(map)
            this.map = map
            addAllShopPins(activities)
        })
    }

    fun loadListFragment(activities: Activities) {

        val listFragment = ActivityListFragment.newInstance(activities)
        listFragment.setItemListener(this)
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.list_activity_fragment, listFragment)
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

    private fun addAllShopPins(activities: Activities) {
        (0 until activities.count())
                .map { activities.get(it) }
                .forEach { addPin(map!!, it) }
    }

    private fun addPin(map: GoogleMap, activity: Activity) {
        val marker = map.addMarker(MarkerOptions().position(LatLng(activity.lat, activity.lon)).title(activity.name))
        marker.tag = activity
        map.setOnInfoWindowClickListener(this)
    }

    //Delegate method from InfoWindowListener
    override fun onInfoWindowClick(marker: Marker?) {
        val selectedActivity = marker!!.tag as Activity
        Navigator().navigateFromMainActivityToDetailActivity(activity as MainActivity, selectedActivity)
        Log.d("MarkerClick", "Click on marker activity: " + selectedActivity.name)
    }

    override fun onItemSelected(activity: Activity, position: Int) {
        Navigator().navigateFromMainActivityToDetailActivity(this.activity as MainActivity, activity)
    }
}