package com.dmw.zgl.bowu.ui.sort;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.api.ApiService;
import com.dmw.zgl.bowu.base.BaseFragment;
import com.dmw.zgl.bowu.base.HttpService;
import com.dmw.zgl.bowu.model.ArticleCoverData;
import com.dmw.zgl.bowu.model.ImageData;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:          dmw
 * Email:           2559531803@qq.com
 * Create:          21:19
 * Update:          21:19
 * Description:     BoWu
 */

public class ArticleListFragment extends BaseFragment {
    private String url;
    private ArticleListAdapter articleListAdapter;

    public static ArticleListFragment getInstance(String url) {
        ArticleListFragment articleListFragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        articleListFragment.setArguments(args);

        return articleListFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_article_list;
    }

    @Override
    protected void initView(View contentView) {
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        articleListAdapter = new ArticleListAdapter();
        recyclerView.setAdapter(articleListAdapter);
    }

    @Override
    protected void setData() {
        this.url = getArguments().getString("url");
        requestData();
    }

    private void requestData() {
        ApiService apiService = HttpService.getInstance().create(ApiService.class);
        apiService.getArticleList(url).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Document>() {
                    @Override
                    public void onNext(@NonNull Document document) {
                        if (document != null) {
                            ArrayList<ArticleCoverData> articleCoverDatas = new ArrayList<>();
                            Elements articleList = document.select("body > div.wpr").get(1).select("ul.article-list > *");
                            for (Element element : articleList) {
                                ArticleCoverData articleCoverData = new ArticleCoverData();
                                ImageData imageData = new ImageData();
                                imageData.url = element.select("div.img > img").first().attr("src");
                                articleCoverData.cover = imageData;
                                articleCoverData.is_free = !element.select("div.img > div.icon-free-red").isEmpty();
                                articleCoverData.name = element.select("div.detail > a").select("h3").first().text();
                                Elements textElements = element.select("div.detail > p");
                                articleCoverData.desc = textElements.first().text();
                                Elements tagElements = textElements.last().select("a");
                                articleCoverData.author = tagElements.first().text();
                                articleCoverData.time = tagElements.last().text();
                                articleCoverDatas.add(articleCoverData);
                            }
                            if (url.endsWith("index.html")) {
                                articleListAdapter.addHomePage(articleCoverDatas);
                            } else {
                                articleListAdapter.addNextPage(articleCoverDatas);
                            }

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
