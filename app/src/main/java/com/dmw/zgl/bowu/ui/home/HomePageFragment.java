package com.dmw.zgl.bowu.ui.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.api.ApiService;
import com.dmw.zgl.bowu.base.BaseFragment;
import com.dmw.zgl.bowu.base.HttpService;
import com.dmw.zgl.bowu.model.ArticleCoverData;
import com.dmw.zgl.bowu.model.HomePageIndexData;
import com.dmw.zgl.bowu.model.ImageData;
import com.dmw.zgl.bowu.model.MagazineCoverData;
import com.dmw.zgl.bowu.model.MoudleTitleData;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 15:43
 * Update:          2017/8/8 15:43
 * Description:     HomePageFragment
 */

public class HomePageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private HomePageAdapter homePageAdapter;
    private HomePageBannerViewHolder homePageBannerViewHolder;

    public static HomePageFragment getInstance() {
        HomePageFragment homePageFragment = new HomePageFragment();
        return homePageFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initView(View contentView) {
        mRefreshLayout = contentView.findViewById(R.id.refreshlayout);
        mRefreshLayout.setOnRefreshListener(this);
        recyclerView = contentView.findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        homePageAdapter = new HomePageAdapter();
        recyclerView.setAdapter(homePageAdapter);
        homePageBannerViewHolder = new HomePageBannerViewHolder(recyclerView);
        homePageAdapter.addHeaderView(homePageBannerViewHolder.getWholeView());

        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void setData() {
        requestData();
    }

    private void requestData() {
        ApiService apiService = HttpService.getInstance().create(ApiService.class);
        apiService.getHomePageIndex()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Document>() {
                    @Override
                    protected void onStart() {
                        mRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onNext(@NonNull Document document) {
                        if (document != null) {
                            HomePageIndexData homePageIndexData = new HomePageIndexData();
                            ImageData imageData = new ImageData();
                            Element topElement = document.select("div.wpr.top-slider").first();
                            Element showImgNameElement = topElement.select("h3").first();
                            imageData.name = showImgNameElement.text();
                            Element imgElement = topElement.select("img").first();
                            imageData.url = imgElement.attr("src");
                            Element descElement = topElement.select("p").first();
                            imageData.desc = descElement.text();
                            homePageIndexData.show_img = imageData;

                            MoudleTitleData articleMoudleTitle = new MoudleTitleData();
                            articleMoudleTitle.title = "推荐阅读";
                            articleMoudleTitle.more = "更多";
                            homePageIndexData.article_moudle_title = articleMoudleTitle;

                            Element contentElement = document.select("div.wpr").select("div.body-left").first();

                            ArrayList<ArticleCoverData> articleCoverDatas = new ArrayList<>();
                            Element atricleContentElement = contentElement.select("div.content").get(0);
                            Elements articleCoverElements = atricleContentElement.select("div.image-block");
                            int articleCoverElementsCount = articleCoverElements.size();
                            for (int i = 0; i < articleCoverElementsCount; i++) {
                                Element articleCover = articleCoverElements.get(i);
                                ArticleCoverData articleCoverData = new ArticleCoverData();
                                ImageData cover = new ImageData();
                                cover.url = articleCover.select("img").first().attr("src");
                                articleCoverData.cover = cover;
                                articleCoverData.name = articleCover.select("h3").select("a").first().text();
                                articleCoverData.author = articleCover.select("span.tips").select("a").first().text();
                                articleCoverData.time = articleCover.select("span.tips.black").select("a").first().text();
                                articleCoverData.left = i % 2 == 0;
                                articleCoverData.right = i % 2 == 1;
                                articleCoverDatas.add(articleCoverData);
                            }
                            homePageIndexData.articles = articleCoverDatas;

                            MoudleTitleData magazinesMoudleTitle = new MoudleTitleData();
                            magazinesMoudleTitle.title = "杂志推荐";
                            magazinesMoudleTitle.more = "更多";
                            homePageIndexData.magazines_moudle_title = magazinesMoudleTitle;

                            ArrayList<MagazineCoverData> magazineCoverDatas = new ArrayList<>();
                            Element magazineContentElement = contentElement.select("div.content").get(1);
                            Elements magazineCoverElements = magazineContentElement.select("div.special-block");
                            for (Element articleCover : magazineCoverElements) {
                                MagazineCoverData magazineCoverData = new MagazineCoverData();
                                ImageData cover = new ImageData();
                                cover.url = articleCover.select("img").first().attr("src");
                                magazineCoverData.cover = cover;
                                magazineCoverData.name = articleCover.select("h3").select("a").first().text();
                                magazineCoverData.time = articleCover.select("span.tips").first().text();
                                magazineCoverData.order = articleCover.select("span.tips.black").first().text();
                                magazineCoverDatas.add(magazineCoverData);
                            }
                            homePageIndexData.magazines = magazineCoverDatas;

                            homePageBannerViewHolder.setData(homePageIndexData.show_img);
                            homePageAdapter.getData().add(homePageIndexData.article_moudle_title);
                            homePageAdapter.getData().addAll(homePageIndexData.articles);
                            homePageAdapter.getData().add(homePageIndexData.magazines_moudle_title);
                            homePageAdapter.getData().addAll(homePageIndexData.magazines);

                            recyclerView.setVisibility(View.VISIBLE);
                            Log.d("okhttp-------------", document.html());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        recyclerView.setVisibility(View.GONE);
                        if (e instanceof SocketTimeoutException) {
                            Toast.makeText(getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                        } else if (e instanceof ConnectException) {
                            Toast.makeText(getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onComplete() {
                        mRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        requestData();
    }
}
