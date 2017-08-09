package com.dmw.zgl.bowu.ui.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.api.ApiService;
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

public class SortFragment extends Fragment {
    private SortFragmentAdapter sortFragmentAdapter;
    public static SortFragment getInstance() {
        SortFragment sortFragment = new SortFragment();
        return sortFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sort, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        sortFragmentAdapter = new SortFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(sortFragmentAdapter);

        requestTypeData();
    }

    private void requestTypeData() {
        ApiService apiService = HttpService.getInstance().create(ApiService.class);
        apiService.getSortIndex().subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Document>() {

                    @Override
                    public void onNext(@NonNull Document document) {
                        if (document != null) {
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
