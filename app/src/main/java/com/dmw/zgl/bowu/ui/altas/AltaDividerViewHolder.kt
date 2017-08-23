package com.dmw.zgl.bowu.ui.altas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dmw.zgl.bowu.R

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 17:54
 * Update:          2017/8/11 17:54
 * Description:     AltaDividerViewHolder
 */

class AltaDividerViewHolder(parent: ViewGroup) {
    val wholeView: View

    init {
        val context = parent.context
        wholeView = LayoutInflater.from(context).inflate(R.layout.item_divider, parent, false)
    }
}
