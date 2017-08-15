package com.dmw.zgl.bowu.ui.magzines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.base.BaseViewGroupAdapter;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/15 10:22
 * Update:          2017/8/15 10:22
 * Description:     MagzinesSelectYearViewHolder
 */

public class MagzinesSelectYearViewHolder {
    private View mWholeView;
    private BaseViewGroupAdapter<String> baseViewGroupAdapter;
    private int selectIndex = 0;

    public MagzinesSelectYearViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.layout_select_year, parent, false);
        FlexboxLayout flexboxLayout = mWholeView.findViewById(R.id.year_layout);
        baseViewGroupAdapter = new BaseViewGroupAdapter<String>(flexboxLayout) {
            @Override
            protected View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_select_year, parent, false);
                }

                TextView year = (TextView) convertView;
                year.setText(getData().get(position));
                year.setSelected(selectIndex == position);
                return convertView;
            }
        };
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(ArrayList<String> years) {
        baseViewGroupAdapter.addHomeData(years);
    }
}
