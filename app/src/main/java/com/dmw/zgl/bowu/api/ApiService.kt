package com.dmw.zgl.bowu.api

import org.jsoup.nodes.Document

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 10:28
 * Update:          2017/8/8 10:28
 * Description:     ApiService
 */

interface ApiService {

    @get:GET("nh/index/index.htm")
    val homePageIndex: Observable<Document>

    @get:GET("nh/tag/index.htm")
    val sortType: Observable<Document>

    @GET
    fun getArticleList(@Url url: String): Observable<Document>

    @GET("nh/pic/{year}.htm")
    fun getAltaList(@Path("year") year: Int): Observable<Document>

    @GET("nh/mag/0/{year}.htm")
    fun getMagzines(@Path("year") year: Int): Observable<Document>
}
