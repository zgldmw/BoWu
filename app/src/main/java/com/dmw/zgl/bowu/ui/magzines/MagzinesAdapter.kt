package com.dmw.zgl.bowu.ui.magzines

import android.view.View
import android.view.ViewGroup

import com.dmw.zgl.bowu.base.RecyclerAdapter
import com.dmw.zgl.bowu.base.RecyclerViewHolder
import com.dmw.zgl.bowu.model.MagazineCoverData

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/15 15:40
 * Update:          2017/8/15 15:40
 * Description:     MagzinesAdapter
 */

class MagzinesAdapter : RecyclerAdapter() {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val magzinesItemViewHolder = MagzinesItemViewHolder(parent)
        val itemView = magzinesItemViewHolder.wholeView
        itemView.tag = magzinesItemViewHolder
        return RecyclerViewHolder(itemView)
    }

    override fun onBindHolder(viewHolder: RecyclerViewHolder, position: Int) {
        val magzinesItemViewHolder = viewHolder.wholeView.tag as MagzinesItemViewHolder
        magzinesItemViewHolder.setData(getData(position) as MagazineCoverData)
    }

    override fun isFullSpan(position: Int): Boolean {
        return false
    }

    override fun getItemType(position: Int): Int {
        return 0
    }
}
