package com.example.challenge.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.example.challenge.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @BindView(R.id.pager) lateinit var viewPager: ViewPager
    @BindView(R.id.tab_layout) lateinit var pagerTabs: TabLayout

    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        pagerAdapter = PagerAdapter(this, supportFragmentManager)
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = pagerAdapter
        pagerTabs.setupWithViewPager(pager)
    }
}
