package com.dmw.zgl.bowu.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.dmw.zgl.bowu.ui.altas.AltasFragment
import com.dmw.zgl.bowu.ui.home.HomePageFragment
import com.dmw.zgl.bowu.ui.magzines.MagzinesFragment
import com.dmw.zgl.bowu.ui.sort.SortFragment

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/10 11:17
 * Update:          2017/8/10 11:17
 * Description:     MagazinesHomeActivityFragmentAdapter
 */

class MagazinesHomeActivityFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val tabTitle = arrayOf("首页", "分类", "图集", "杂志")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomePageFragment.instance
            1 -> SortFragment.instance
            2 -> AltasFragment.instance
            3 -> MagzinesFragment.instance
            else -> HomePageFragment.instance
        }
    }

    override fun getCount(): Int {
        return tabTitle.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitle[position]
    }
}
