package com.dmw.zgl.bowu.ui.mainhome

import android.util.Log
import android.view.View
import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.base.BaseFragment

/**
 * Author:          dmw
 * Email:           2559531803@qq.com
 * Create:          23:23
 * Update:          23:23
 * Description:     BoWu
 */
class HomeFragment : BaseFragment() {
    override fun setContentView(): Int {
        return R.layout.fragment_home
    }

    override fun initView(contentView: View?) {
        Log.d(javaClass.simpleName, contentView.toString())
    }

    override fun setData() {
        Log.d(javaClass.simpleName, "not do")
    }

    companion object {
        val instance
            get() = HomeFragment()
    }
}