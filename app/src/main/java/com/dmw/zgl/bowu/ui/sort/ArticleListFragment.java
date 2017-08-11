package com.dmw.zgl.bowu.ui.sort;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.api.ApiService;
import com.dmw.zgl.bowu.base.BaseFragment;
import com.dmw.zgl.bowu.base.HttpService;
import com.dmw.zgl.bowu.base.LoadMoreHandler;
import com.dmw.zgl.bowu.base.LoadMoreItemView;
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

public class ArticleListFragment extends BaseFragment implements LoadMoreHandler {
    private String url;
    private int totalPage;
    private int currentPage = 1;
    private boolean isLoading;
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

        LoadMoreItemView loadMoreItemView = new LoadMoreItemView();
        loadMoreItemView.setupWithRecyclerView(recyclerView);
        loadMoreItemView.setLoadMoreHandler(this);
    }

    @Override
    protected void setData() {
        url = getArguments().getString("url");
        if (url != null) {
            url = url.split(".htm")[0];
        }
        requestData();
    }

    private void requestData() {
        ApiService apiService = HttpService.getInstance().create(ApiService.class);
        apiService.getArticleList(url + "/" + currentPage + ".html").subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Document>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        isLoading = true;
                    }

                    @Override
                    public void onNext(@NonNull Document document) {
                        isLoading = false;
                        if (document != null) {
                            Element content = document.select("body > div.wpr").get(1);

                            Elements pages = content.select("div.pagination").first().select("li");
                            totalPage = pages.last().hasText() ? Integer.parseInt(pages.last().text()) : Integer.parseInt(pages.get(pages.size() - 2).text());
                            int activePage = Integer.parseInt(pages.select(".active").first().text());
                            if (currentPage != activePage) {
                                return;
                            }

                            ArrayList<ArticleCoverData> articleCoverDatas = new ArrayList<>();
                            Elements articleList = content.select("ul.article-list > *");
                            for (Element element : articleList) {
                                ArticleCoverData articleCoverData = new ArticleCoverData();
                                ImageData imageData = new ImageData();
                                Element img = element.select("div.img > img").first();
                                imageData.url = img == null ? "" : img.attr("src");
                                articleCoverData.cover = imageData;
                                articleCoverData.is_free = !element.select("div.img > div.icon-free-red").isEmpty();
                                articleCoverData.name = element.select("div.detail > a").select("h3").first().text();
                                Elements textElements = element.select("div.detail > p");
                                articleCoverData.desc = textElements.first().text();
                                Elements tagElements = textElements.last().select("a");
                                Element author = tagElements.select("[href*=/author/]").first();
                                articleCoverData.author = author == null ? "" : author.text();
                                Element time = tagElements.select("[href*=/detail/]").first();
                                articleCoverData.time = time == null ? "" : time.text();
                                articleCoverDatas.add(articleCoverData);
                            }
                            if (currentPage == 1) {
                                articleListAdapter.addHomePage(articleCoverDatas);
                            } else {
                                articleListAdapter.addNextPage(articleCoverDatas);
                            }
                            currentPage++;

                            Log.d("ArticleListFragment", document.html());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoading = false;
                    }

                    @Override
                    public void onComplete() {
                        isLoading = false;
                    }
                });
    }

    @Override
    public boolean isCanLoadMore() {
        return totalPage > currentPage;
    }

    @Override
    public void loadMore() {
        if (!isLoading) {
            requestData();
        }
    }
}
