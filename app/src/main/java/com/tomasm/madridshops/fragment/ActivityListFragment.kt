package com.tomasm.madridshops.fragment


import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tomasm.madridshops.R
import com.tomasm.madridshops.adapter.ActivitiesRecyclerViewAdapter
import madridshops.tomasm.com.domain.model.Activities


class ActivityListFragment : Fragment() {

    lateinit var rootView: View
    lateinit var itemsList: RecyclerView
    private var onItemSelectedListener: OnItemSelectedListener? = null

    companion object {

        private val ARG_ACTIVITIES = "argActivities"

        fun newInstance(activities: Activities): ActivityListFragment {
            val fragment = ActivityListFragment()

            val args = Bundle()
            args.putSerializable(ARG_ACTIVITIES, activities)
            fragment.arguments = args
            return fragment
        }
    }

    interface OnItemSelectedListener {
        fun onItemSelected(item: madridshops.tomasm.com.domain.model.Activity, position: Int)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (inflater != null) {

            rootView = inflater.inflate(R.layout.fragment_activity_list, container, false)

            itemsList = rootView.findViewById(R.id.activity_list)
            itemsList.layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.recycler_columns_amount))
            itemsList.itemAnimator = DefaultItemAnimator()

            if (arguments != null) {
                val activities  = arguments.getSerializable(ARG_ACTIVITIES) as Activities

                val itemsAdapter = ActivitiesRecyclerViewAdapter(activities)
                itemsList.adapter = itemsAdapter

                itemsAdapter.onClickListener = View.OnClickListener { v:View? ->
                    //Aca me entero que se pulsó una de las vistas, v es la vista que fue pulsada
                    val position = itemsList.getChildAdapterPosition(v)
                    val itemSelected = activities.get(position)
                    onItemSelectedListener?.onItemSelected(itemSelected,position)
                }
            } else {
                //TODO: ERROR NO ACTIVITIES RECEIVED
            }
        }

        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        onItemSelectedListener = null
    }

    fun setItemListener(listener: ActivityListFragment.OnItemSelectedListener) {
        onItemSelectedListener = listener
    }

}
