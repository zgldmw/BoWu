package com.dmw.zgl.bowu.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import com.dmw.zgl.bowu.R

class MagazinesHomeActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magzines_home)

        initView()
    }

    private fun initView() {
        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        viewPager.setPageTransformer(true, null)
        val fragmentAdapter = MagazinesHomeActivityFragmentAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
    }
}
