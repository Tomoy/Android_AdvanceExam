package com.tomasm.madridshops.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_main.*
import com.tomasm.madridshops.R
import com.tomasm.madridshops.fragment.ShopActivityPagerFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Picasso setup
        Picasso.with(this).setIndicatorsEnabled(false) //Muestra un indicador rojo cuando la imagen no estaba en cache y verde cuando lee de cache
        Picasso.with(this).isLoggingEnabled = true  //Logea lo que Picasso va haciendo

        if (findViewById<View>(R.id.fragment_sections_pager) != null) {
            if (fragmentManager.findFragmentById(R.id.fragment_sections_pager) == null) {
                val fragment = ShopActivityPagerFragment.newInstance(0)
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_sections_pager, fragment)
                        .commit()
            }
        }
    }
}
