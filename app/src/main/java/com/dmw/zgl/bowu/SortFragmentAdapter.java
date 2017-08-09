package com.dmw.zgl.bowu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/9 18:03
 * Update:          2017/8/9 18:03
 * Description:     SortFragmentAdapter
 */

public class SortFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private ArrayList<ArticleTypeCategoryData> categoryDatas;

    public SortFragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        categoryDatas = new ArrayList<>();
    }

    public void addHomeDatas(ArrayList<Fragment> fragments) {
        this.fragments.clear();
        if (fragments != null) {
            this.fragments.addAll(fragments);
        }
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryDatas.get(position).type;
    }
}
