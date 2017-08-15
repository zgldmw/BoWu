package com.dmw.zgl.bowu.ui.magzines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.model.MagazineCoverData;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/15 15:04
 * Update:          2017/8/15 15:04
 * Description:     MagzinesItemViewHolder
 */

public class MagzinesItemViewHolder {
    private View mWholeView;

    private LinearLayout mDetailLayout;
    private TextView title;
    private TextView order;
    private SimpleDraweeView cover;

    public MagzinesItemViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.item_magzines, parent, false);

        mDetailLayout = mWholeView.findViewById(R.id.detial_layout);
        title = mWholeView.findViewById(R.id.title);
        order = mWholeView.findViewById(R.id.order);
        cover = mWholeView.findViewById(R.id.cover);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(MagazineCoverData magazineCoverData) {
        title.setText(magazineCoverData.name);
        order.setText(magazineCoverData.order);
        cover.setImageURI(magazineCoverData.cover.url);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mDetailLayout.getLayoutParams();
        layoutParams.setMargins(0, cover.getHeight(), 0, 0);
        mDetailLayout.setLayoutParams(layoutParams);
    }
}
