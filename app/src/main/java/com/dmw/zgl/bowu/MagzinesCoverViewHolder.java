package com.dmw.zgl.bowu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.model.MagazineCoverData;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 17:14
 * Update:          2017/8/8 17:14
 * Description:     MagzinesCoverViewHolder
 */

public class MagzinesCoverViewHolder {
    private View mWholeView;
    private Context mContext;

    private SimpleDraweeView cover;
    private TextView name;
    private TextView time;
    private TextView order;

    public MagzinesCoverViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.item_magzines_cover, parent, false);
        cover = mWholeView.findViewById(R.id.cover);
        name = mWholeView.findViewById(R.id.name);
        time = mWholeView.findViewById(R.id.time);
        order = mWholeView.findViewById(R.id.order);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(MagazineCoverData magazineCoverData) {
        int paddingLeft = magazineCoverData.left ? mContext.getResources().getDimensionPixelOffset(R.dimen.px30) : mContext.getResources().getDimensionPixelOffset(R.dimen.px15);
        int paddingRight = magazineCoverData.right ? mContext.getResources().getDimensionPixelOffset(R.dimen.px30) : mContext.getResources().getDimensionPixelOffset(R.dimen.px15);
        mWholeView.setPadding(paddingLeft, 0, paddingRight, mContext.getResources().getDimensionPixelOffset(R.dimen.px30));

        cover.setImageURI(magazineCoverData.cover.url);
        name.setText(magazineCoverData.name);
        time.setText(magazineCoverData.time);
        order.setText(magazineCoverData.order);
    }
}
