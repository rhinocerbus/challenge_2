package com.example.challenge.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.challenge.view.basic.BasicViewFragment
import com.example.challenge.view.chonk.ChonkViewFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> BasicViewFragment()
            1 -> ChonkViewFragment()
            else -> throw UnsupportedOperationException()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}