package com.dmw.zgl.bowu.ui.altas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmw.zgl.bowu.R;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 17:54
 * Update:          2017/8/11 17:54
 * Description:     AltaDividerViewHolder
 */

public class AltaDividerViewHolder {
    private View mWholeView;

    public AltaDividerViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.item_divider, parent, false);
    }

    public View getWholeView() {
        return mWholeView;
    }
}
