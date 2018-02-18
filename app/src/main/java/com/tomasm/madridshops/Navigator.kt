package com.tomasm.madridshops

import com.tomasm.madridshops.activity.DetailActivity
import com.tomasm.madridshops.activity.MainActivity
import madridshops.tomasm.com.domain.model.Shop

//Mejor extrar las navegaciones de rutas a otros fragments o Activities asi es mas limpio, claro y mas facil pasar parámetros
class Navigator {

    fun navigateFromMainActivityToDetailActivity(main: MainActivity, selectedShop: Shop) {
        main.startActivity(DetailActivity.intent(main, selectedShop))
    }
}
