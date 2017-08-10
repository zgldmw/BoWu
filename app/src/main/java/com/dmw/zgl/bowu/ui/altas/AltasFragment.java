package com.dmw.zgl.bowu.ui.altas;

import android.support.v4.app.Fragment;
import android.view.View;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.base.BaseFragment;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 15:45
 * Update:          2017/8/8 15:45
 * Description:     AltasFragment
 */

public class AltasFragment extends BaseFragment {
    public static AltasFragment getInstance() {
        AltasFragment altasFragment = new AltasFragment();
        return altasFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void initView(View contentView) {

    }

    @Override
    protected void setData() {

    }
}
