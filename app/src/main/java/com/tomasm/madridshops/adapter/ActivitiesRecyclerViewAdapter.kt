package com.tomasm.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.tomasm.madridshops.R
import madridshops.tomasm.com.domain.model.Activities
import madridshops.tomasm.com.domain.model.Activity

class ActivitiesRecyclerViewAdapter (val activities: Activities?) : RecyclerView.Adapter<ActivitiesRecyclerViewAdapter.ActivitiesViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ActivitiesViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        itemView.setOnClickListener(onClickListener)
        return ActivitiesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActivitiesViewHolder?, position: Int) {
        if (activities != null) {
            holder?.bindItem(activities.get(position))
        }
    }

    override fun getItemCount(): Int {
        return activities?.count() ?: 0
    }

    inner class ActivitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemNameTextView = itemView.findViewById<TextView>(R.id.item_name)
        val itemLogoImageView = itemView.findViewById<ImageView>(R.id.item_logo)

        fun bindItem(activity: Activity) {

            val context = itemLogoImageView.context

            itemNameTextView.text = activity.name

            Picasso.with(context)
                    .load(activity.logoImgUrl)
                    .placeholder(R.drawable.loading)
                    .into(itemLogoImageView)
        }
    }
}