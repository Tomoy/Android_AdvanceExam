package com.tomasm.madridshops

import android.content.Intent
import com.tomasm.madridshops.activity.DetailActivity
import com.tomasm.madridshops.activity.MainActivity
import com.tomasm.madridshops.activity.PicassoActivity
import madridshops.tomasm.com.domain.model.Shop

//Mejor extrar las navegaciones de rutas a otros fragments o Activities asi es mas limpio, claro y mas facil pasar parámetros
class Navigator {

    fun navigateFromMainActivityToPicassoActivity(main: MainActivity) {
        main.startActivity(Intent(main, PicassoActivity::class.java))
    }

    fun navigateFromMainActivityToDetailActivity(main: MainActivity, selectedShop: Shop) {
        main.startActivity(DetailActivity.intent(main, selectedShop))
    }
}
