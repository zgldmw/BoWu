package com.dmw.zgl.bowu.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.model.MagazineCoverData
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 17:14
 * Update:          2017/8/8 17:14
 * Description:     MagzinesCoverViewHolder
 */

class MagzinesCoverViewHolder(parent: ViewGroup) {
    val wholeView: View
    private val mContext: Context = parent.context

    private val cover: SimpleDraweeView
    private val name: TextView
    private val time: TextView
    private val order: TextView

    init {
        wholeView = LayoutInflater.from(mContext).inflate(R.layout.item_magzines_cover, parent, false)
        cover = wholeView.findViewById(R.id.cover)
        name = wholeView.findViewById(R.id.name)
        time = wholeView.findViewById(R.id.time)
        order = wholeView.findViewById(R.id.order)
    }

    fun setData(magazineCoverData: MagazineCoverData) {
        cover.setImageURI(magazineCoverData.cover!!.url)
        name.text = magazineCoverData.name
        time.text = magazineCoverData.time
        order.text = magazineCoverData.order
    }
}
