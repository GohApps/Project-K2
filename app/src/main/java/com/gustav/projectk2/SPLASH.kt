package com.gustav.projectk2

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class SPLASH : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Handler().postDelayed({

           // findNavController().navigate(R.id.action_SPLASH_to_viewPagerFragment)
            if(isBoardingFinished()){
                findNavController().navigate(R.id.action_SPLASH_to_homeFragment)
            }
            else {
                findNavController().navigate(R.id.action_SPLASH_to_viewPagerFragment)
            }


        }, 100)



        return inflater.inflate(R.layout.fragment_s_p_l_a_s_h, container, false)
    }

    private fun isBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("FragmentSharedPref", Context.MODE_PRIVATE)
             return       sharedPref.getBoolean("OnBoardingFinished", false)
    }

}