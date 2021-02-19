package com.gustav.projectk2.onBoarding.boardingscreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gustav.projectk2.R
import com.gustav.projectk2.ViewPagerAdapter
import com.gustav.projectk2.onBoarding.boardingscreens.FirstScreen
import com.gustav.projectk2.onBoarding.boardingscreens.SecondScreen
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

class OnboardingViewPagerFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_view_pager, container, false)
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        view.viewPager.adapter = adapter

        return view
    }

}