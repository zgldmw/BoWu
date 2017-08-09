package com.dmw.zgl.bowu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmw.zgl.bowu.model.ArticleCoverData;
import com.dmw.zgl.bowu.model.HomePageIndexData;
import com.dmw.zgl.bowu.model.ImageData;
import com.dmw.zgl.bowu.model.MagazineCoverData;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 15:43
 * Update:          2017/8/8 15:43
 * Description:     HomePageFragment
 */

public class HomePageFragment extends Fragment {
    private HomePageAdapter homePageAdapter;
    private HomePageBannerViewHolder homePageBannerViewHolder;

    public static HomePageFragment getInstance() {
        HomePageFragment homePageFragment = new HomePageFragment();
        return homePageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        homePageAdapter = new HomePageAdapter();
        recyclerView.setAdapter(homePageAdapter);
        homePageBannerViewHolder = new HomePageBannerViewHolder(recyclerView);
        homePageAdapter.addHeaderView(homePageBannerViewHolder.getWholeView());

        requestData();
    }

    private void requestData() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("OkHttp-----------------", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(HtmlConverterFactory.create())
                .baseUrl("http://www.dili360.com/nh/")
                .client(okHttpClient)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Document> call = apiService.getHomePageIndex();
        call.enqueue(new Callback<Document>() {
            @Override
            public void onResponse(Call<Document> call, Response<Document> response) {
                Document document = response.body();
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

                    Element contentElement = document.select("div.wpr").select("div.body-left").first();

                    ArrayList<ArticleCoverData> articleCoverDatas = new ArrayList<>();
                    Element atricleContentElement = contentElement.select("div.content").first();
                    Elements articleCoverElements = atricleContentElement.select("div.image-block");
                    for (Element articleCover : articleCoverElements) {
                        ArticleCoverData articleCoverData = new ArticleCoverData();
                        ImageData cover = new ImageData();
                        cover.url = articleCover.select("img").first().attr("src");
                        articleCoverData.cover = cover;
                        articleCoverData.name = articleCover.select("h3").select("a").first().text();
                        articleCoverData.author = articleCover.select("span.tips").select("a").first().text();
                        articleCoverData.time = articleCover.select("span.tips.black").select("a").first().text();
                        articleCoverDatas.add(articleCoverData);
                    }
                    homePageIndexData.articles = articleCoverDatas;

                    ArrayList<MagazineCoverData> magazineCoverDatas = new ArrayList<>();
                    Element magazineContentElement = contentElement.select("div.content").last();
                    Elements magazineCoverElements = magazineContentElement.select("div.image-block");
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
                    homePageAdapter.addHomePage(homePageIndexData.articles);
                    homePageAdapter.addNextPage(homePageIndexData.magazines);
                }
                Log.d("okhttp-------------", response.body().html());
            }

            @Override
            public void onFailure(Call<Document> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
