package com.tomasm.madridshops.fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tomasm.madridshops.R
import com.tomasm.madridshops.adapter.ShopsRecyclerViewAdapter
import madridshops.tomasm.com.domain.model.Shop
import madridshops.tomasm.com.domain.model.Shops


class ShopListFragment : Fragment() {

    lateinit var rootView: View
    lateinit var itemsList: RecyclerView
    private var onItemSelectedListener: OnItemSelectedListener? = null

    companion object {

        private val ARG_SHOPS = "argShops"

        fun newInstance(shops: Shops): ShopListFragment {
            val fragment = ShopListFragment()

            val args = Bundle()
            args.putSerializable(ARG_SHOPS, shops)
            fragment.arguments = args
            return fragment
        }
    }

    interface OnItemSelectedListener {
        fun onItemSelected(item: Shop, position: Int)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (inflater != null) {

            rootView = inflater.inflate(R.layout.fragment_list, container, false)

            itemsList = rootView.findViewById(R.id.items_list)
            itemsList.layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.recycler_columns_amount))
            itemsList.itemAnimator = DefaultItemAnimator()

            if (arguments != null) {
                val shops  = arguments.getSerializable(ARG_SHOPS) as Shops

                val itemsAdapter = ShopsRecyclerViewAdapter(shops)
                itemsList.adapter = itemsAdapter

                itemsAdapter.onClickListener = View.OnClickListener { v:View? ->
                    //Aca me entero que se pulsó una de las vistas, v es la vista que fue pulsada
                    val position = itemsList.getChildAdapterPosition(v)
                    val itemSelected = shops.get(position)
                    onItemSelectedListener?.onItemSelected(itemSelected,position)
                }
            } else {
                //TODO: ERROR NO SHOPS RECEIVED
            }
        }

        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonOnAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        onItemSelectedListener = null
    }

    fun commonOnAttach(listener:Any?) {
        if (listener is OnItemSelectedListener) {
            onItemSelectedListener = listener
        }
    }

}
