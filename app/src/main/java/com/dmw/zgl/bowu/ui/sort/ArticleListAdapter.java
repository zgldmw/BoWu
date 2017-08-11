package com.dmw.zgl.bowu.ui.sort;

import android.view.ViewGroup;

import com.dmw.zgl.bowu.base.RecyclerAdapter;
import com.dmw.zgl.bowu.base.RecyclerViewHolder;
import com.dmw.zgl.bowu.model.ArticleCoverData;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/10 14:55
 * Update:          2017/8/10 14:55
 * Description:     ArticleListAdapter
 */

public class ArticleListAdapter extends RecyclerAdapter {
    @Override
    protected RecyclerViewHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        ItemArticleListViewHolder itemArticleListViewHolder = new ItemArticleListViewHolder(viewGroup);
        itemArticleListViewHolder.getWholeView().setTag(itemArticleListViewHolder);
        return new RecyclerViewHolder(itemArticleListViewHolder.getWholeView());
    }

    @Override
    protected void onBindHolder(RecyclerViewHolder viewHolder, int position) {
        ItemArticleListViewHolder itemArticleListViewHolder = (ItemArticleListViewHolder) viewHolder.getWholeView().getTag();
        itemArticleListViewHolder.setData((ArticleCoverData) getData(position));
    }

    @Override
    protected boolean isFullSpan(int position) {
        return true;
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }
}
