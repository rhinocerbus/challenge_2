package com.example.challenge.view

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.challenge.R
import com.example.challenge.view.anim1.AnimatedViewFragment
import com.example.challenge.view.basic.BasicViewFragment
import com.example.challenge.view.chonk.ChonkViewFragment

class PagerAdapter(val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val frag =  when(position) {
            0 -> BasicViewFragment()
            1 -> ChonkViewFragment()
            2 -> AnimatedViewFragment()
            else -> throw UnsupportedOperationException()
        }
        frag.userVisibleHint = false
        return frag
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val id = when(position) {
            0 -> R.string.tab_basic
            1 -> R.string.tab_chonk
            2 -> R.string.tab_animated
            else -> throw UnsupportedOperationException()
        }

        return context.getString(id)
    }
}