package com.dmw.zgl.bowu.ui

import android.util.Log
import android.view.View
import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.base.BaseFragment

/**
 * Author:          dmw
 * Email:           2559531803@qq.com
 * Create:          23:28
 * Update:          23:28
 * Description:     BoWu
 */
class CommunityFragment : BaseFragment() {
    override fun setContentView(): Int {
        return R.layout.empty_fragment
    }

    override fun initView(contentView: View?) {
        Log.d(javaClass.simpleName, contentView.toString())
    }

    override fun setData() {
        Log.d(javaClass.simpleName, "not do")
    }

    companion object {
        val instance
            get() = CommunityFragment()
    }
}