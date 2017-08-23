package com.dmw.zgl.bowu.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.model.MoudleTitleData

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/9 17:15
 * Update:          2017/8/9 17:15
 * Description:     MoudleTitleViewHolder
 */

class MoudleTitleViewHolder(parent: ViewGroup) {
    val wholeView: View

    private val title: TextView
    private val more: TextView

    init {
        val context = parent.context
        wholeView = LayoutInflater.from(context).inflate(R.layout.item_moudle_title, parent, false)
        title = wholeView.findViewById(R.id.title)
        more = wholeView.findViewById(R.id.more)
    }

    fun setData(moudleTitleData: MoudleTitleData) {
        title.text = moudleTitleData.title
        more.text = moudleTitleData.more
    }
}
