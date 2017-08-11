package com.dmw.zgl.bowu.ui.altas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 16:46
 * Update:          2017/8/11 16:46
 * Description:     AltasYeartitleViewHolder
 */

public class AltasYeartitleViewHolder {
    private View mWholeView;

    private TextView year;

    public AltasYeartitleViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.item_alta_title, parent, false);
        year = mWholeView.findViewById(R.id.year);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(String time) {
        year.setText(time);
    }
}
