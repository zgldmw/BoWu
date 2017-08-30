package com.dmw.zgl.bowu.ui.mainhome

import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.base.FrescoUtils
import com.dmw.zgl.bowu.model.ImageData
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Author:          dmw
 * Email:           2559531803@qq.com
 * Create:          17:55
 * Update:          17:55
 * Description:     BoWu
 */

class HomeNewsViewHolder(parent: ViewGroup) : ViewPager.OnPageChangeListener {
    var wholeView: View
    private var count = 0
    private var currentItem = 0
    private val viewPager: ViewPager
    private val tabLayout: TabLayout
    private val adapter : BannerAdapter
    private val handler: Handler
    private val task : Runnable

    init {
        val context = parent.context
        wholeView = LayoutInflater.from(context).inflate(R.layout.layout_home_news, parent, false)
        viewPager = wholeView.findViewById(R.id.viewpager)
        adapter = BannerAdapter(ArrayList())
        viewPager.adapter = adapter
        tabLayout = wholeView.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        handler = Handler()
        task = object : Runnable {
            override fun run() {
                currentItem = (currentItem + 1) % count
                viewPager.currentItem = currentItem
                handler.postDelayed(this, 1000)
            }
        }
    }

    fun setData(datas: ArrayList<ImageData>) {
        count = datas.size
        adapter.addHomeData(datas)
        handler.postDelayed(task, 1000)
    }

    override fun onPageScrollStateChanged(state: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageSelected(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class BannerAdapter(private val imgs: ArrayList<ImageData>) : PagerAdapter() {
        fun addHomeData(datas: ArrayList<ImageData>) {
            imgs.clear()
            imgs.addAll(datas)
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val bannerItem = BannerItem(container!!)
            bannerItem.setData(imgs[position])
            container.addView(bannerItem.wholeView)
            return bannerItem.wholeView
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container?.removeView(`object` as View?)
        }

        override fun getCount(): Int {
            return imgs.size
        }

    }

    inner class BannerItem(parent: ViewGroup) {
        private val context = parent.context
        val wholeView = LayoutInflater.from(context).inflate(R.layout.item_banner, parent, false)!!
        private val bannerImage: SimpleDraweeView

        init {
            bannerImage = wholeView.findViewById(R.id.banner_image)
        }

        fun setData(imageData: ImageData) {
            FrescoUtils.displayImgAspectRatio(bannerImage, imageData.url)
        }
    }

}
