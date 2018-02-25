package com.tomasm.madridshops.fragment

import android.app.Fragment
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.tomasm.madridshops.common.Map
import madridshops.tomasm.com.domain.model.Section

abstract class BaseSectionFragment : Fragment(), GoogleMap.OnInfoWindowClickListener {
    companion object {

        fun newInstance(initialSection: Section): BaseSectionFragment =
                if (initialSection.name == "Shops") {
                    ShopSectionFragment()
                } else {
                    ActivitySectionFragment()
                }

    }

    protected fun setUpMap(map: GoogleMap) {
        Log.d("SUCCESS", "Habemus MAPA")
        Map().centerMapInPosition(map, 40.416775, -3.703790, 13f)
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isZoomControlsEnabled = true
        Map().showUserPosition(activity.baseContext, map, activity) //baseContext porque estamos dentro de una clausura y no tenemos acceso al context como this
    }
}
