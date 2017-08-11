package com.dmw.zgl.bowu.api;

import org.jsoup.nodes.Document;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 10:28
 * Update:          2017/8/8 10:28
 * Description:     ApiService
 */

public interface ApiService {

    @GET("nh/index/index.htm")
    Observable<Document> getHomePageIndex();

    @GET("nh/tag/index.htm")
    Observable<Document> getSortType();

    @GET
    Observable<Document> getArticleList(@Url String url);

    @GET("nh/pic/{year}.htm")
    Observable<Document> getAltaList(@Path("year") int year);
}
