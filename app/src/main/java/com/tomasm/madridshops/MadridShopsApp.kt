package com.tomasm.madridshops

import android.support.multidex.MultiDexApplication
import android.util.Log
import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.interactor.getAll.GetAllShopsInteractorImplementation
import madridshops.tomasm.com.domain.model.Shops

//Sería como el AppDelegate de iOS, una clase inicial por proyecto
class MadridShopsApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}