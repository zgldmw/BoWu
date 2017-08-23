package com.dmw.zgl.bowu.ui.sort

import android.view.ViewGroup

import com.dmw.zgl.bowu.base.RecyclerAdapter
import com.dmw.zgl.bowu.base.RecyclerViewHolder
import com.dmw.zgl.bowu.model.ArticleCoverData

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/10 14:55
 * Update:          2017/8/10 14:55
 * Description:     ArticleListAdapter
 */

class ArticleListAdapter : RecyclerAdapter() {
    override fun onCreateHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemArticleListViewHolder = ItemArticleListViewHolder(viewGroup)
        itemArticleListViewHolder.wholeView.tag = itemArticleListViewHolder
        return RecyclerViewHolder(itemArticleListViewHolder.wholeView)
    }

    override fun onBindHolder(viewHolder: RecyclerViewHolder, position: Int) {
        val itemArticleListViewHolder = viewHolder.wholeView.tag as ItemArticleListViewHolder
        itemArticleListViewHolder.setData(getData(position) as ArticleCoverData)
    }

    override fun isFullSpan(position: Int): Boolean {
        return true
    }

    override fun getItemType(position: Int): Int {
        return 0
    }
}
