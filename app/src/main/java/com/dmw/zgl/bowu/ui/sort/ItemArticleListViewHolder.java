package com.dmw.zgl.bowu.ui.sort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.base.FreeTagView;
import com.dmw.zgl.bowu.model.ArticleCoverData;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/10 11:56
 * Update:          2017/8/10 11:56
 * Description:     ItemArticleListViewHolder
 */

public class ItemArticleListViewHolder {
    private View mWholeView;
    private Context mContext;

    private SimpleDraweeView cover;
    private FreeTagView freeTagView;
    private TextView name;
    private TextView desc;
    private TextView author;
    private TextView time;

    public ItemArticleListViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        mWholeView = LayoutInflater.from(mContext).inflate(R.layout.item_article_list, parent, false);
        cover = mWholeView.findViewById(R.id.cover);
        freeTagView = mWholeView.findViewById(R.id.free_tag);
        name = mWholeView.findViewById(R.id.name);
        desc = mWholeView.findViewById(R.id.desc);
        author = mWholeView.findViewById(R.id.author);
        time = mWholeView.findViewById(R.id.time);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(ArticleCoverData articleCoverData) {
        cover.setImageURI(articleCoverData.cover.url);
        freeTagView.setVisibility(articleCoverData.is_free ? View.VISIBLE : View.GONE);
        name.setText(articleCoverData.name);
        desc.setText(articleCoverData.desc);
        author.setText(mContext.getResources().getString(R.string.author, articleCoverData.author));
        time.setText(mContext.getResources().getString(R.string.time, articleCoverData.time));
    }
}

