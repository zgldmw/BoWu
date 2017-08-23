package com.dmw.zgl.bowu.ui.sort

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.view.ViewGroup

import com.dmw.zgl.bowu.model.ArticleTypeCategoryData

import java.util.ArrayList

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/9 18:03
 * Update:          2017/8/9 18:03
 * Description:     SortFragmentAdapter
 */

class SortFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val categoryDatas: ArrayList<ArticleTypeCategoryData> = ArrayList()

    fun addCategoryDatas(categoryDatas: ArrayList<ArticleTypeCategoryData>) {
        this.categoryDatas.clear()
        this.categoryDatas.addAll(categoryDatas)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        // TODO: 2017/8/9 根据类型个数创建fragment
        return ArticleListFragment.getInstance(categoryDatas[position].url!!)
    }

    override fun getCount(): Int {
        return categoryDatas.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return categoryDatas[position].type!!
    }
}
