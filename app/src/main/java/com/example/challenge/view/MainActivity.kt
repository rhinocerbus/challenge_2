package com.example.challenge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.challenge.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagerAdapter = PagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter
    }
}
