package com.dmw.zgl.bowu.ui.magzines

import android.content.Context
import android.support.v7.widget.RecyclerView
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
 * Create:          2017/8/15 15:04
 * Update:          2017/8/15 15:04
 * Description:     MagzinesItemViewHolder
 */

class MagzinesItemViewHolder(parent: ViewGroup) {
    val wholeView: View
    private val context: Context = parent.context

    private val title: TextView
    private val order: TextView
    private val cover: SimpleDraweeView

    init {
        wholeView = LayoutInflater.from(context).inflate(R.layout.item_magzines, parent, false)
        title = wholeView.findViewById(R.id.title)
        order = wholeView.findViewById(R.id.order)
        cover = wholeView.findViewById(R.id.cover)
    }

    fun setData(magazineCoverData: MagazineCoverData) {
        val padding = context.resources.getDimensionPixelOffset(R.dimen.px20)
        val layoutParams = wholeView.layoutParams as RecyclerView.LayoutParams
        if (magazineCoverData.left) {
            layoutParams.setMargins(padding, padding, 0, padding)
        } else {
            layoutParams.setMargins(0, padding, padding, padding)
        }
        wholeView.layoutParams = layoutParams
        title.text = magazineCoverData.name
        order.text = magazineCoverData.order
        cover.setImageURI(magazineCoverData.cover!!.url)
    }
}
