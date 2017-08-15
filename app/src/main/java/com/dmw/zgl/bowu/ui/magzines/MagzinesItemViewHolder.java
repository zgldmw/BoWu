package com.dmw.zgl.bowu.ui.magzines;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private Context context;

    private TextView title;
    private TextView order;
    private SimpleDraweeView cover;

    public MagzinesItemViewHolder(ViewGroup parent) {
        context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.item_magzines, parent, false);

        title = mWholeView.findViewById(R.id.title);
        order = mWholeView.findViewById(R.id.order);
        cover = mWholeView.findViewById(R.id.cover);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(MagazineCoverData magazineCoverData) {
        int padding = context.getResources().getDimensionPixelOffset(R.dimen.px20);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mWholeView.getLayoutParams();
        if (magazineCoverData.left) {
            layoutParams.setMargins(padding, padding, 0, padding);
        } else {
            layoutParams.setMargins(0, padding, padding, padding);
        }
        mWholeView.setLayoutParams(layoutParams);
        title.setText(magazineCoverData.name);
        order.setText(magazineCoverData.order);
        cover.setImageURI(magazineCoverData.cover.url);
    }
}
