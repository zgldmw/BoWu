package com.dmw.zgl.bowu.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.model.ImageData
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 16:00
 * Update:          2017/8/8 16:00
 * Description:     HomePageBannerViewHolder
 */

class HomePageBannerViewHolder(parent: ViewGroup) {
    val wholeView: View

    private val img: SimpleDraweeView
    private val name: TextView
    private val desc: TextView

    init {
        val context = parent.context
        wholeView = LayoutInflater.from(context).inflate(R.layout.layout_homepage_banner, parent, false)
        img = wholeView.findViewById(R.id.img)
        name = wholeView.findViewById(R.id.name)
        desc = wholeView.findViewById(R.id.desc)
    }

    fun setData(imageData: ImageData) {
        img.setImageURI(imageData.url)
        name.text = imageData.name
        desc.text = imageData.desc
    }

}
