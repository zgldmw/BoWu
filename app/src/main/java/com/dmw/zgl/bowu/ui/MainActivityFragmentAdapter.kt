package com.dmw.zgl.bowu.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.dmw.zgl.bowu.ui.mainhome.HomeFragment

/**
 * Author:          dmw
 * Email:           2559531803@qq.com
 * Create:          23:29
 * Update:          23:29
 * Description:     BoWu
 */
class MainActivityFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val tabTitle = arrayOf("首页", "发现", "活动", "社区")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.instance
            1 -> FindFragment.instance
            2 -> ActiveFragment.instance
            3 -> CommunityFragment.instance
            else -> HomeFragment.instance
        }
    }

    override fun getCount(): Int {
        return tabTitle.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitle[position]
    }
}