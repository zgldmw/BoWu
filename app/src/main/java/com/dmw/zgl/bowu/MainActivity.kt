package com.dmw.zgl.bowu

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager

import com.dmw.zgl.bowu.base.HttpService
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imagePipelineConfig = HttpService.initPipeline(this)
        Fresco.initialize(this, imagePipelineConfig)

        initView()
    }

    private fun initView() {
        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        viewPager.setPageTransformer(true, null)
        val fragmentAdapter = HomeActivityFragmentAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
    }
}
