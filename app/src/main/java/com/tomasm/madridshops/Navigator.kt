package com.tomasm.madridshops

import android.content.Intent
import com.tomasm.madridshops.activity.MainActivity
import com.tomasm.madridshops.activity.PicassoActivity

//Mejor extrar las navegaciones de rutas a otros fragments o Activities asi es mas limpio, claro y mas facil pasar parámetros
class Navigator {

    fun navigateFromMainActivityToPicassoActivity(main: MainActivity) {
        main.startActivity(Intent(main, PicassoActivity::class.java))

    }
}
