package com.dmw.zgl.bowu.ui.home

import android.view.View
import android.view.ViewGroup

import com.dmw.zgl.bowu.base.RecyclerAdapter
import com.dmw.zgl.bowu.base.RecyclerViewHolder
import com.dmw.zgl.bowu.model.ArticleCoverData
import com.dmw.zgl.bowu.model.MagazineCoverData
import com.dmw.zgl.bowu.model.MoudleTitleData

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 16:39
 * Update:          2017/8/8 16:39
 * Description:     HomePageAdapter
 */

class HomePageAdapter : RecyclerAdapter() {

    override fun onCreateHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView: View
        when (viewType) {
            ARTICLE -> {
                val articleCoverViewHolder = ArticleCoverViewHolder(viewGroup)
                itemView = articleCoverViewHolder.wholeView
                itemView.tag = articleCoverViewHolder
            }
            MAGZINES -> {
                val magzinesCoverViewHolder = MagzinesCoverViewHolder(viewGroup)
                itemView = magzinesCoverViewHolder.wholeView
                itemView.tag = magzinesCoverViewHolder
            }
            else -> {
                val moudleTitleViewHolder = MoudleTitleViewHolder(viewGroup)
                itemView = moudleTitleViewHolder.wholeView
                itemView.tag = moudleTitleViewHolder
            }
        }
        return RecyclerViewHolder(itemView)
    }

    override fun onBindHolder(viewHolder: RecyclerViewHolder, position: Int) {
        val viewType = getItemType(position)
        when (viewType) {
            ARTICLE -> {
                val articleCoverViewHolder = viewHolder.wholeView.tag as ArticleCoverViewHolder
                articleCoverViewHolder.setData(getData(position) as ArticleCoverData)
            }
            MAGZINES -> {
                val magzinesCoverViewHolder = viewHolder.wholeView.tag as MagzinesCoverViewHolder
                magzinesCoverViewHolder.setData(getData(position) as MagazineCoverData)
            }
            else -> {
                val moudleTitleViewHolder = viewHolder.wholeView.tag as MoudleTitleViewHolder
                moudleTitleViewHolder.setData(getData(position) as MoudleTitleData)
            }
        }
    }

    override fun isFullSpan(position: Int): Boolean {
        return getItemViewType(position) == MAGZINES || getItemViewType(position) == TITLE
    }

    override fun getItemType(position: Int): Int {
        val o = getData(position)
        return when (o) {
            is ArticleCoverData -> ARTICLE
            is MagazineCoverData -> MAGZINES
            else -> TITLE
        }
    }

    companion object {
        val ARTICLE = 0
        val MAGZINES = 1
        val TITLE = 2
    }
}
