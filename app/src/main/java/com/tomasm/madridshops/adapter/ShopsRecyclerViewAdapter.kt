package com.tomasm.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.tomasm.madridshops.R
import kotlinx.android.synthetic.main.activity_picasso.*
import madridshops.tomasm.com.domain.model.Shop
import madridshops.tomasm.com.domain.model.Shops

/**
 * Created by TomasM on 2/18/18.
 */
class ShopsRecyclerViewAdapter (val shops: Shops?) : RecyclerView.Adapter<ShopsRecyclerViewAdapter.ShopsViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShopsViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        itemView.setOnClickListener(onClickListener)
        return ShopsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShopsViewHolder?, position: Int) {
        if (shops != null) {
            holder?.bindItem(shops.get(position))
        }
    }

    override fun getItemCount(): Int {
        return shops?.count() ?: 0
    }

    inner class ShopsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemNameTextView = itemView.findViewById<TextView>(R.id.item_name)
        val itemLogoImageView = itemView.findViewById<ImageView>(R.id.item_logo)

        fun bindItem(shop: Shop) {

            //Accedemos al contexto del Item a través de una de sus vistas
            val context = itemLogoImageView.context

            //Actualizamos la vista con el modelo
            itemNameTextView.text = shop.name

            //Cargamos la imagen desde la url con picasso para que la cachee tambíen
            Picasso.with(context)
                    .load(shop.logoImgUrl)
                    .placeholder(R.drawable.loading)
                    .into(itemLogoImageView)
        }
    }
}