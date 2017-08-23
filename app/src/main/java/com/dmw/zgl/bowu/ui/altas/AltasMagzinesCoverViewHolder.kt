package com.dmw.zgl.bowu.ui.altas

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
 * Create:          2017/8/11 17:04
 * Update:          2017/8/11 17:04
 * Description:     AltasMagzinesCoverViewHolder
 */

class AltasMagzinesCoverViewHolder(parent: ViewGroup) {
    val wholeView: View

    private val cover: SimpleDraweeView
    private val title: TextView
    private val order: TextView

    init {
        val context = parent.context
        wholeView = LayoutInflater.from(context).inflate(R.layout.item_altas_magzines_cover, parent, false)
        cover = wholeView.findViewById(R.id.cover)
        title = wholeView.findViewById(R.id.title)
        order = wholeView.findViewById(R.id.order)
    }

    fun setData(magazineCoverData: MagazineCoverData) {
        cover.setImageURI(magazineCoverData.cover!!.url)
        title.text = magazineCoverData.name
        order.text = magazineCoverData.order
    }
}
