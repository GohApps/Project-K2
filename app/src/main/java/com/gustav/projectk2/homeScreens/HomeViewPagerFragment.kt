package com.gustav.projectk2.homeScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.gustav.projectk2.R
import com.gustav.projectk2.ViewPagerAdapter
import com.gustav.projectk2.homeScreens.filed_notes.FiledNotesFragment
import com.gustav.projectk2.homeScreens.open_notes.OpenNotesFragment
import com.gustav.projectk2.homeScreens.template.TemplateFragment
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

class HomeViewPagerFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_view_pager, container, false)
        val fragmentList = arrayListOf<Fragment>(
            TemplateFragment(),
                OpenNotesFragment(),
            FiledNotesFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        view.viewPager.offscreenPageLimit = 2
        
        view.viewPager.adapter = adapter

        TabLayoutMediator(view.tabLayout, view.viewPager,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    when (position) {
                        0 -> { tab.text = "Templates"}
                        1 -> { tab.text = "Open notes"}
                        2 -> { tab.text = "Filed notes"}
                    }
                }).attach()

        return view
    }

}