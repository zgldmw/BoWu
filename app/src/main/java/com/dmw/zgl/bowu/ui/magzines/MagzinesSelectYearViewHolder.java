package com.dmw.zgl.bowu.ui.magzines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.base.BaseViewGroupAdapter;
import com.dmw.zgl.bowu.model.MagzinesSelectYearData;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/15 10:22
 * Update:          2017/8/15 10:22
 * Description:     MagzinesSelectYearViewHolder
 */

public class MagzinesSelectYearViewHolder implements View.OnClickListener {
    private View mWholeView;
    private FlexboxLayout flexboxLayout;
    private BaseViewGroupAdapter<MagzinesSelectYearData> baseViewGroupAdapter;
    private ArrayList<MagzinesSelectYearData> datas;
    private OnSelectYearListener onSelectYearListener;

    public MagzinesSelectYearViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.layout_select_year, parent, false);
        mWholeView.setVisibility(View.GONE);
        flexboxLayout = mWholeView.findViewById(R.id.year_layout);
        baseViewGroupAdapter = new BaseViewGroupAdapter<MagzinesSelectYearData>(flexboxLayout) {
            @Override
            protected View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_select_year, parent, false);
                    convertView.setOnClickListener(MagzinesSelectYearViewHolder.this);
                }

                MagzinesSelectYearData magzinesSelectYearData = getData().get(position);
                TextView year = (TextView) convertView;
                year.setText(magzinesSelectYearData.year);
                year.setSelected(magzinesSelectYearData.active);
                return convertView;
            }
        };
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(ArrayList<MagzinesSelectYearData> years) {
        datas = years;
        mWholeView.setVisibility(View.VISIBLE);
        baseViewGroupAdapter.addHomeData(datas);
    }

    @Override
    public void onClick(View view) {
        int selectIndex = 0;
        int count = flexboxLayout.getFlexItemCount();
        for (int i = 0; i < count; i++) {
            View childView = flexboxLayout.getFlexItemAt(i);
            if (childView.isSelected()) {
                childView.setSelected(false);
            }
            if (childView == view) {
                selectIndex = i;
            }
        }
        view.setSelected(true);
        if (onSelectYearListener != null) {
            onSelectYearListener.onSelectYear(Integer.parseInt(datas.get(selectIndex).year));
        }
    }

    public void setOnSelectYearListener(OnSelectYearListener onSelectYearListener) {
        this.onSelectYearListener = onSelectYearListener;
    }

    public interface OnSelectYearListener {
        /**
         * Called when select a year.
         */
        void onSelectYear(int year);
    }
}
