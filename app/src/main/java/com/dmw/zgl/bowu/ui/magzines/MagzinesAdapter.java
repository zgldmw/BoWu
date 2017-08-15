package com.dmw.zgl.bowu.ui.magzines;

import android.view.View;
import android.view.ViewGroup;

import com.dmw.zgl.bowu.base.RecyclerAdapter;
import com.dmw.zgl.bowu.base.RecyclerViewHolder;
import com.dmw.zgl.bowu.model.MagazineCoverData;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/15 15:40
 * Update:          2017/8/15 15:40
 * Description:     MagzinesAdapter
 */

public class MagzinesAdapter extends RecyclerAdapter {
    @Override
    protected RecyclerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        MagzinesItemViewHolder magzinesItemViewHolder = new MagzinesItemViewHolder(parent);
        View itemView = magzinesItemViewHolder.getWholeView();
        itemView.setTag(magzinesItemViewHolder);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    protected void onBindHolder(RecyclerViewHolder viewHolder, int position) {
        MagzinesItemViewHolder magzinesItemViewHolder = (MagzinesItemViewHolder) viewHolder.getWholeView().getTag();
        magzinesItemViewHolder.setData((MagazineCoverData) getData(position));
    }

    @Override
    protected boolean isFullSpan(int position) {
        return false;
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }
}
