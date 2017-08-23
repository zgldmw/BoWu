package com.dmw.zgl.bowu.ui.sort

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.base.FreeTagView
import com.dmw.zgl.bowu.model.ArticleCoverData
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/10 11:56
 * Update:          2017/8/10 11:56
 * Description:     ItemArticleListViewHolder
 */

class ItemArticleListViewHolder(parent: ViewGroup) {
    val wholeView: View
    private val mContext: Context = parent.context

    private val cover: SimpleDraweeView
    private val freeTagView: FreeTagView
    private val name: TextView
    private val desc: TextView
    private val author: TextView
    private val time: TextView

    init {
        wholeView = LayoutInflater.from(mContext).inflate(R.layout.item_article_list, parent, false)
        cover = wholeView.findViewById(R.id.cover)
        freeTagView = wholeView.findViewById(R.id.free_tag)
        name = wholeView.findViewById(R.id.name)
        desc = wholeView.findViewById(R.id.desc)
        author = wholeView.findViewById(R.id.author)
        time = wholeView.findViewById(R.id.time)
    }

    fun setData(articleCoverData: ArticleCoverData) {
        cover.setImageURI(articleCoverData.cover!!.url)
        freeTagView.visibility = if (articleCoverData.is_free) View.VISIBLE else View.GONE
        name.text = articleCoverData.name
        desc.text = articleCoverData.desc
        desc.visibility = if (TextUtils.isEmpty(articleCoverData.desc)) View.GONE else View.VISIBLE
        author.text = mContext.resources.getString(R.string.author, articleCoverData.author)
        author.visibility = if (TextUtils.isEmpty(articleCoverData.author)) View.GONE else View.VISIBLE
        time.text = mContext.resources.getString(R.string.time, articleCoverData.time)
        time.visibility = if (TextUtils.isEmpty(articleCoverData.time)) View.GONE else View.VISIBLE
    }
}

