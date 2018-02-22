package com.tomasm.madridshops.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v13.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.tomasm.madridshops.R
import madridshops.tomasm.com.domain.model.Sections

class ShopActivityPagerFragment : Fragment() {

    companion object {
        val ARG_INITIAL_INDEX = "ARG_INITIAL_INDEX"

        fun newInstance(initialIndex: Int): ShopActivityPagerFragment {
            val arguments = Bundle()
            arguments.putInt(ARG_INITIAL_INDEX, initialIndex)
            val fragment = ShopActivityPagerFragment()
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var root: View
    val pager by lazy { root.findViewById<ViewPager>(R.id.view_pager) }

    var initialIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_pager, container, false)

            initialIndex = arguments?.getInt(ARG_INITIAL_INDEX) ?: 0

            val adapter = object: FragmentPagerAdapter(fragmentManager) {
                override fun getItem(position: Int) = SectionFragment.newInstance(Sections[position])

                override fun getCount() = Sections.count

                override fun getPageTitle(position: Int) = Sections[position].name
            }

            pager.adapter = adapter

            pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    updateCityInfo(position)
                }

            })

            pager.currentItem = initialIndex
            updateCityInfo(initialIndex)
        }

        return root
    }

    /*override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.pager, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.previous -> {
            pager.currentItem = pager.currentItem - 1
            true
        }
        R.id.next -> {
            pager.currentItem++
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        val menuPrev = menu?.findItem(R.id.previous)
        menuPrev?.setEnabled(pager.currentItem > 0)

        val menuNext = menu?.findItem(R.id.next)
        menuNext?.setEnabled(pager.currentItem < Cities.count - 1)
    }*/

    fun updateCityInfo(position: Int) {
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar
            supportActionBar?.title = Sections[position].name
        }
    }
}