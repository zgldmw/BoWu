package com.dmw.zgl.bowu;

import org.jsoup.nodes.Document;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 10:28
 * Update:          2017/8/8 10:28
 * Description:     ApiService
 */

public interface ApiService {

    @GET("index/index.htm")
    Observable<Document> getHomePageIndex();

    @GET("nh/tag/index.htm")
    Observable<Document> getSortIndex();
}