package com.dmw.zgl.bowu;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 16:26
 * Update:          2017/8/8 16:26
 * Description:     RecyclerViewHolder
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private View mWholeView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mWholeView = itemView;
    }

    public View getWholeView() {
        return mWholeView;
    }
}
