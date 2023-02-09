package com.example.roomdatabase.pagination.Fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class   TabAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) :
    FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return HomeFragment()

            }
            1 -> {
                return FavouriteFragment()
            }
            else -> return HomeFragment()
        }

    }

    override fun getCount(): Int {
        return totalTabs

    }


}