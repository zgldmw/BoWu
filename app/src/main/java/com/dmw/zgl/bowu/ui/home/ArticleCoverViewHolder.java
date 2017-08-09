package com.dmw.zgl.bowu.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.model.ArticleCoverData;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 16:41
 * Update:          2017/8/8 16:41
 * Description:     ArticleCoverViewHolder
 */

public class ArticleCoverViewHolder {
    private View mWholeView;
    private Context mContext;

    private SimpleDraweeView cover;
    private TextView name;
    private TextView author;
    private TextView time;

    public ArticleCoverViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        mWholeView = LayoutInflater.from(mContext).inflate(R.layout.item_article_cover, parent, false);
        cover = mWholeView.findViewById(R.id.cover);
        name = mWholeView.findViewById(R.id.name);
        author = mWholeView.findViewById(R.id.author);
        time = mWholeView.findViewById(R.id.time);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(ArticleCoverData articleCoverData) {
        int paddingLeft = articleCoverData.left ? mContext.getResources().getDimensionPixelOffset(R.dimen.px30) : mContext.getResources().getDimensionPixelOffset(R.dimen.px15);
        int paddingRight = articleCoverData.right ? mContext.getResources().getDimensionPixelOffset(R.dimen.px30) : mContext.getResources().getDimensionPixelOffset(R.dimen.px15);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mWholeView.getLayoutParams();
        layoutParams.setMargins(paddingLeft, 0, paddingRight, mContext.getResources().getDimensionPixelOffset(R.dimen.px30));

        cover.setImageURI(articleCoverData.cover.url);
        name.setText(articleCoverData.name);
        author.setText(articleCoverData.author);
        time.setText(articleCoverData.time);
    }
}
