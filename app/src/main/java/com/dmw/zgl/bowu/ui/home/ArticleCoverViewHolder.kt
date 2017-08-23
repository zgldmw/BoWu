package com.dmw.zgl.bowu.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.model.ArticleCoverData
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 16:41
 * Update:          2017/8/8 16:41
 * Description:     ArticleCoverViewHolder
 */

class ArticleCoverViewHolder(parent: ViewGroup) {
    val wholeView: View
    private val mContext: Context = parent.context
    private val px30 = mContext.resources.getDimensionPixelOffset(R.dimen.px30)
    private val px15 = mContext.resources.getDimensionPixelOffset(R.dimen.px15)

    private val cover: SimpleDraweeView
    private val name: TextView
    private val author: TextView
    private val time: TextView

    init {
        wholeView = LayoutInflater.from(mContext).inflate(R.layout.item_article_cover, parent, false)
        cover = wholeView.findViewById(R.id.cover)
        name = wholeView.findViewById(R.id.name)
        author = wholeView.findViewById(R.id.author)
        time = wholeView.findViewById(R.id.time)
    }

    fun setData(articleCoverData: ArticleCoverData) {
        val paddingLeft = if (articleCoverData.left) px30 else px15
        val paddingRight = if (articleCoverData.right) px30 else px15
        val layoutParams = wholeView.layoutParams as RecyclerView.LayoutParams
        layoutParams.setMargins(paddingLeft, 0, paddingRight, px30)

        cover.setImageURI(articleCoverData.cover!!.url)
        name.text = articleCoverData.name
        author.text = articleCoverData.author
        time.text = articleCoverData.time
    }
}
