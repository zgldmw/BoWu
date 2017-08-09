package com.dmw.zgl.bowu.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.model.MoudleTitleData;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/9 17:15
 * Update:          2017/8/9 17:15
 * Description:     MoudleTitleViewHolder
 */

public class MoudleTitleViewHolder {
    private View mWholeView;

    private TextView title;
    private TextView more;

    public MoudleTitleViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.item_moudle_title, parent, false);
        title = mWholeView.findViewById(R.id.title);
        more = mWholeView.findViewById(R.id.more);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(MoudleTitleData moudleTitleData) {
        title.setText(moudleTitleData.title);
        more.setText(moudleTitleData.more);
    }
}
