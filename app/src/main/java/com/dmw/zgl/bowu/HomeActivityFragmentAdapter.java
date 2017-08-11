package com.dmw.zgl.bowu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dmw.zgl.bowu.base.BaseFragment;
import com.dmw.zgl.bowu.ui.altas.AltasFragment;
import com.dmw.zgl.bowu.ui.home.HomePageFragment;
import com.dmw.zgl.bowu.ui.magzines.MagzinesFragment;
import com.dmw.zgl.bowu.ui.sort.SortFragment;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/10 11:17
 * Update:          2017/8/10 11:17
 * Description:     HomeActivityFragmentAdapter
 */

public class HomeActivityFragmentAdapter extends FragmentPagerAdapter {
    private String[] tabTitle = new String[]{"首页", "分类", "图集", "杂志"};

    public HomeActivityFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment;
        switch (position) {
            case 0:
                fragment = HomePageFragment.getInstance();
                break;
            case 1:
                fragment = SortFragment.getInstance();
                break;
            case 2:
                fragment = AltasFragment.getInstance();
                break;
            case 3:
                fragment = MagzinesFragment.getInstance();
                break;
            default:
                fragment = HomePageFragment.getInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
