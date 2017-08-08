package com.dmw.zgl.bowu;

import android.view.View;
import android.view.ViewGroup;

import com.dmw.zgl.bowu.model.ArticleCoverData;
import com.dmw.zgl.bowu.model.MagazineCoverData;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 16:39
 * Update:          2017/8/8 16:39
 * Description:     HomePageAdapter
 */

public class HomePageAdapter extends RecyclerAdapter {
    public static final int ARTICLE = 0;
    public static final int MAGZINES = 1;

    @Override
    protected RecyclerViewHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        View itemView;
        if (viewType == ARTICLE) {
            ArticleCoverViewHolder articleCoverViewHolder = new ArticleCoverViewHolder(viewGroup);
            itemView = articleCoverViewHolder.getWholeView();
            itemView.setTag(articleCoverViewHolder);
        } else {
            MagzinesCoverViewHolder magzinesCoverViewHolder = new MagzinesCoverViewHolder(viewGroup);
            itemView = magzinesCoverViewHolder.getWholeView();
            itemView.setTag(magzinesCoverViewHolder);
        }
        return new RecyclerViewHolder(itemView);
    }

    @Override
    protected void onBindHolder(RecyclerViewHolder viewHolder, int position) {
        int viewType = getItemType(position);
        if (viewType == ARTICLE) {
            ArticleCoverViewHolder articleCoverViewHolder = (ArticleCoverViewHolder) viewHolder.getWholeView().getTag();
            articleCoverViewHolder.setData((ArticleCoverData) getData(position));
        } else {
            MagzinesCoverViewHolder magzinesCoverViewHolder = (MagzinesCoverViewHolder) viewHolder.getWholeView().getTag();
            magzinesCoverViewHolder.setData((MagazineCoverData) getData(position));
        }
    }

    @Override
    protected boolean isFullSpan(int position) {
        return false;
    }

    @Override
    protected int getItemType(int position) {
        Object o = getData(position);
        if (o instanceof ArticleCoverData) {
            return ARTICLE;
        } else if (o instanceof MagazineCoverData) {
            return MAGZINES;
        }
        return 0;
    }
}
