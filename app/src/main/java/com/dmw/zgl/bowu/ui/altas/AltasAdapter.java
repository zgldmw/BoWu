package com.dmw.zgl.bowu.ui.altas;

import android.view.View;
import android.view.ViewGroup;

import com.dmw.zgl.bowu.base.RecyclerAdapter;
import com.dmw.zgl.bowu.base.RecyclerViewHolder;
import com.dmw.zgl.bowu.model.AltasYearData;
import com.dmw.zgl.bowu.model.ImageData;
import com.dmw.zgl.bowu.model.MagazineCoverData;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 16:45
 * Update:          2017/8/11 16:45
 * Description:     AltasAdapter
 */

public class AltasAdapter extends RecyclerAdapter {
    private static final int TITLE = 0;
    private static final int COVER = 1;
    private static final int ALTA = 2;
    private static final int DIVIDER = 3;
    @Override
    protected RecyclerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TITLE) {
            AltasYeartitleViewHolder altasYeartitleViewHolder = new AltasYeartitleViewHolder(parent);
            itemView = altasYeartitleViewHolder.getWholeView();
        } else if (viewType == COVER) {
            AltasMagzinesCoverViewHolder altasMagzinesCoverViewHolder = new AltasMagzinesCoverViewHolder(parent);
            itemView = altasMagzinesCoverViewHolder.getWholeView();
        } else if (viewType == ALTA) {
            AltaItemViewholder altaItemViewholder = new AltaItemViewholder(parent);
            itemView = altaItemViewholder.getWholeView();
        } else {
            AltaDividerViewHolder altaDividerViewHolder = new AltaDividerViewHolder(parent);
            itemView = altaDividerViewHolder.getWholeView();
        }

        itemView.setTag(itemView);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    protected void onBindHolder(RecyclerViewHolder viewHolder, int position) {
        int viewType = getItemType(position);
        if (viewType == TITLE) {
            AltasYeartitleViewHolder altasYeartitleViewHolder = (AltasYeartitleViewHolder) viewHolder.getWholeView().getTag();
            altasYeartitleViewHolder.setData((String) getData(position));
        } else if (viewType == COVER) {
            AltasMagzinesCoverViewHolder altasMagzinesCoverViewHolder = (AltasMagzinesCoverViewHolder) viewHolder.getWholeView().getTag();
            altasMagzinesCoverViewHolder.setData((MagazineCoverData) getData(position));
        } else if (viewType == ALTA) {
            AltaItemViewholder altaItemViewholder = (AltaItemViewholder) viewHolder.getWholeView().getTag();
            altaItemViewholder.setData((ImageData) getData(position));
        }
    }

    @Override
    protected boolean isFullSpan(int position) {
        int viewType = getItemViewType(position);
        return viewType == TITLE || viewType == DIVIDER;
    }

    @Override
    protected int getItemType(int position) {
        int viewType;
        Object o = getData(position);
        if (o instanceof AltasYearData) {
            viewType = TITLE;
        } else if (o instanceof MagazineCoverData) {
            viewType = COVER;
        } else if (o instanceof ImageData) {
            viewType = ALTA;
        } else {
            viewType = DIVIDER;
        }
        return viewType;
    }
}
