package com.dmw.zgl.bowu.ui.sort;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.dmw.zgl.bowu.model.ArticleTypeCategoryData;

import java.util.ArrayList;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/9 18:03
 * Update:          2017/8/9 18:03
 * Description:     SortFragmentAdapter
 */

public class SortFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<ArticleTypeCategoryData> categoryDatas;

    public SortFragmentAdapter(FragmentManager fm) {
        super(fm);
        categoryDatas = new ArrayList<>();
    }

    public void setCategoryDatas(ArrayList<ArticleTypeCategoryData> categoryDatas) {
        this.categoryDatas = categoryDatas;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        // TODO: 2017/8/9 根据类型个数创建fragment
        return ArticleListFragment.getInstance(categoryDatas.get(position).url);
    }

    @Override
    public int getCount() {
        return categoryDatas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryDatas.get(position).type;
    }
}
