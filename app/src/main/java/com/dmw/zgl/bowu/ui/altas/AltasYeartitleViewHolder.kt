package com.dmw.zgl.bowu.ui.altas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dmw.zgl.bowu.R

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 16:46
 * Update:          2017/8/11 16:46
 * Description:     AltasYeartitleViewHolder
 */

class AltasYeartitleViewHolder(parent: ViewGroup) {
    val wholeView: View

    private val year: TextView

    init {
        val context = parent.context
        wholeView = LayoutInflater.from(context).inflate(R.layout.item_alta_title, parent, false)
        year = wholeView.findViewById(R.id.year)
    }

    fun setData(time: String) {
        year.text = time
    }
}
