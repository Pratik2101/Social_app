package com.shivtej.socialapp.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shivtej.socialapp.Fragments.SignInFragment
import com.shivtej.socialapp.Fragments.SignupFragment

@Suppress("DEPRICATION")
class MyAdapter(var context: Context, fm: FragmentManager, var totaltabs: Int) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return totaltabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 ->{
                SignInFragment()
            }
            1 ->{
                SignupFragment()
            }
            else -> getItem(position)
        }
    }


}