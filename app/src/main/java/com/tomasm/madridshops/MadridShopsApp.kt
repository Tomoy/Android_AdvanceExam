package com.tomasm.madridshops

import android.support.multidex.MultiDexApplication
import android.util.Log
import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.interactor.getAllShops.GetAllShopsInteractorImplementation
import madridshops.tomasm.com.domain.model.Shops

//Sería como el AppDelegate de iOS, una clase inicial por proyecto
class MadridShopsApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        //Init code application

        Log.d("SERVER_URL", BuildConfig.MADRID_SHOPS_SERVER_URL)

        val allShopsInteractor = GetAllShopsInteractorImplementation(this)

        //Le paso el objeto SuccessCompletion para que me lo devuelvan con los shops
        allShopsInteractor.execute(object: SuccessCompletion<Shops> {
            override fun successCompletion(shops: Shops) {
                Log.d( "Shops", "Count: " + shops.count())
                shops.shops.forEach{ Log.d("Shop: ", it.name)}
            }

        }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Log.d( "Error", "Error: " + errorMessage)
            }
        })

/*        DeleteAllShopsImplementation(this).execute(success = {
            Log.d("Success", "Successful deletion")
        }, error = {
            Log.d("Error", "Error deleting all shops" + it)
        })*/
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}