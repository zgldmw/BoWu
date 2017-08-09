package com.dmw.zgl.bowu.ui.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmw.zgl.bowu.R;

/**
 * Author:          dmw
 * Email:           2559531803@qq.com
 * Create:          21:19
 * Update:          21:19
 * Description:     BoWu
 */

public class ArticleListFragment extends Fragment {
    private  String url;

    public static ArticleListFragment getInstance(String url) {
        ArticleListFragment articleListFragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        articleListFragment.setArguments(args);

        return articleListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.url = getArguments().getString("url");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
