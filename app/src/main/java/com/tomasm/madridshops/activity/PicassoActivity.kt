package com.tomasm.madridshops.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.tomasm.madridshops.R
import kotlinx.android.synthetic.main.activity_picasso.*

class PicassoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picasso)

        Picasso.with(this).setIndicatorsEnabled(true) //Muestra un indicador rojo cuando la imagen no estaba en cache y verde cuando lee de cache
        Picasso.with(this).isLoggingEnabled = true  //Logea lo que Picasso va haciendo

        Picasso.with(this)
                .load("http://37.media.tumblr.com/tumblr_ktna2fMosM1qa02x4o1_400.jpg")
                .placeholder(android.R.drawable.ic_delete)
                .into(img1)

        Picasso.with(this)
                .load("http://i0.kym-cdn.com/photos/images/facebook/001/217/729/f9a.jpg")
                .placeholder(android.R.drawable.ic_delete)
                .into(img2)

        Picasso.with(this)
                .load("https://vignette.wikia.nocookie.net/dbz-dokkanbattle/images/3/33/Please-meme.jpg")
                .placeholder(android.R.drawable.ic_delete)
                .into(img3)
    }
}
