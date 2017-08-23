package com.dmw.zgl.bowu.ui.altas

import android.view.View
import android.view.ViewGroup

import com.dmw.zgl.bowu.base.RecyclerAdapter
import com.dmw.zgl.bowu.base.RecyclerViewHolder
import com.dmw.zgl.bowu.model.ImageData
import com.dmw.zgl.bowu.model.MagazineCoverData

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 16:45
 * Update:          2017/8/11 16:45
 * Description:     AltasAdapter
 */

class AltasAdapter : RecyclerAdapter() {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView: View
        when (viewType) {
            TITLE -> {
                val altasYeartitleViewHolder = AltasYeartitleViewHolder(parent)
                itemView = altasYeartitleViewHolder.wholeView
                itemView.tag = altasYeartitleViewHolder
            }
            COVER -> {
                val altasMagzinesCoverViewHolder = AltasMagzinesCoverViewHolder(parent)
                itemView = altasMagzinesCoverViewHolder.wholeView
                itemView.tag = altasMagzinesCoverViewHolder
            }
            ALTA -> {
                val altaItemViewholder = AltaItemViewholder(parent)
                itemView = altaItemViewholder.wholeView
                itemView.tag = altaItemViewholder
            }
            else -> {
                val altaDividerViewHolder = AltaDividerViewHolder(parent)
                itemView = altaDividerViewHolder.wholeView
                itemView.tag = altaDividerViewHolder
            }
        }

        return RecyclerViewHolder(itemView)
    }

    override fun onBindHolder(viewHolder: RecyclerViewHolder, position: Int) {
        val viewType = getItemType(position)
        when (viewType) {
            TITLE -> {
                val altasYeartitleViewHolder = viewHolder.wholeView.tag as AltasYeartitleViewHolder
                altasYeartitleViewHolder.setData(getData(position) as String)
            }
            COVER -> {
                val altasMagzinesCoverViewHolder = viewHolder.wholeView.tag as AltasMagzinesCoverViewHolder
                altasMagzinesCoverViewHolder.setData(getData(position) as MagazineCoverData)
            }
            ALTA -> {
                val altaItemViewholder = viewHolder.wholeView.tag as AltaItemViewholder
                altaItemViewholder.setData(getData(position) as ImageData)
            }
        }
    }

    override fun isFullSpan(position: Int): Boolean {
        val viewType = getItemViewType(position)
        return viewType == TITLE || viewType == DIVIDER
    }

    override fun getItemType(position: Int): Int {
        val viewType: Int
        val o = getData(position)
        viewType = when (o) {
            is String -> TITLE
            is MagazineCoverData -> COVER
            is ImageData -> ALTA
            else -> DIVIDER
        }
        return viewType
    }

    companion object {
        private val TITLE = 0
        private val COVER = 1
        private val ALTA = 2
        private val DIVIDER = 3
    }
}
