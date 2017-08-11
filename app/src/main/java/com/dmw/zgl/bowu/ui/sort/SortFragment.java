package com.dmw.zgl.bowu.ui.sort;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.api.ApiService;
import com.dmw.zgl.bowu.base.BaseFragment;
import com.dmw.zgl.bowu.base.HttpService;
import com.dmw.zgl.bowu.model.ArticleTypeCategoryData;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 15:44
 * Update:          2017/8/8 15:44
 * Description:     SortFragment
 */

public class SortFragment extends BaseFragment {
    private SortFragmentAdapter sortFragmentAdapter;

    public static SortFragment getInstance() {
        SortFragment sortFragment = new SortFragment();
        return sortFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void initView(View contentView) {
        ViewPager viewPager = contentView.findViewById(R.id.viewpager);
        sortFragmentAdapter = new SortFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(sortFragmentAdapter);

        TabLayout tabLayout = contentView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void setData() {
        requestTypeData();
    }

    private void requestTypeData() {
        ApiService apiService = HttpService.getInstance().create(ApiService.class);
        apiService.getSortType().subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Document>() {

                    @Override
                    public void onNext(@NonNull Document document) {
                        ArrayList<ArticleTypeCategoryData> typeCategoryDatas = new ArrayList<>();
                        Elements types = document.select("body > div.wpr").first().select("a");
                        for (Element element : types) {
                            ArticleTypeCategoryData typeCategoryData = new ArticleTypeCategoryData();
                            typeCategoryData.type = element.text();
                            typeCategoryData.url = element.attr("href");
                            typeCategoryDatas.add(typeCategoryData);
                        }
                        sortFragmentAdapter.setCategoryDatas(typeCategoryDatas);

                        Log.d("SortFragment", types.html());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
