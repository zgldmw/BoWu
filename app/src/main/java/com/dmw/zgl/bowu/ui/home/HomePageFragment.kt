package com.dmw.zgl.bowu.ui.home

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.api.ApiService
import com.dmw.zgl.bowu.base.BaseFragment
import com.dmw.zgl.bowu.base.HttpService
import com.dmw.zgl.bowu.model.ArticleCoverData
import com.dmw.zgl.bowu.model.HomePageIndexData
import com.dmw.zgl.bowu.model.ImageData
import com.dmw.zgl.bowu.model.MagazineCoverData
import com.dmw.zgl.bowu.model.MoudleTitleData

import org.jsoup.nodes.Document

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.ArrayList

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 15:43
 * Update:          2017/8/8 15:43
 * Description:     HomePageFragment
 */

class HomePageFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    private var recyclerView: RecyclerView? = null
    private var mRefreshLayout: SwipeRefreshLayout? = null
    private var homePageAdapter: HomePageAdapter? = null
    private var homePageBannerViewHolder: HomePageBannerViewHolder? = null

    override fun setContentView(): Int {
        return R.layout.fragment_homepage
    }

    override fun initView(contentView: View?) {
        mRefreshLayout = contentView!!.findViewById(R.id.refreshlayout)
        mRefreshLayout!!.setOnRefreshListener(this)
        recyclerView = contentView.findViewById(R.id.recyclerview)
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView!!.layoutManager = gridLayoutManager
        homePageAdapter = HomePageAdapter()
        recyclerView!!.adapter = homePageAdapter
        homePageBannerViewHolder = HomePageBannerViewHolder(recyclerView!!)
        homePageAdapter!!.addHeaderView(homePageBannerViewHolder!!.wholeView)

        recyclerView!!.visibility = View.GONE
    }

    override fun setData() {
        requestData()
    }

    private fun requestData() {
        val apiService = HttpService.initRetrofit()!!.create(ApiService::class.java)
        apiService.homePageIndex
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Document>() {
                    override fun onStart() {
                        mRefreshLayout!!.isRefreshing = true
                    }

                    override fun onNext(document: Document) {
                        val homePageIndexData = HomePageIndexData()
                        val imageData = ImageData()
                        val topElement = document.select("div.wpr.top-slider").first()
                        val showImgNameElement = topElement.select("h3").first()
                        imageData.name = showImgNameElement.text()
                        val imgElement = topElement.select("img").first()
                        imageData.url = imgElement.attr("src")
                        val descElement = topElement.select("p").first()
                        imageData.desc = descElement.text()
                        homePageIndexData.show_img = imageData

                        val articleMoudleTitle = MoudleTitleData()
                        articleMoudleTitle.title = "推荐阅读"
                        articleMoudleTitle.more = "更多"
                        homePageIndexData.article_moudle_title = articleMoudleTitle

                        val contentElement = document.select("div.wpr").select("div.body-left").first()

                        val articleCoverDatas = ArrayList<ArticleCoverData>()
                        val atricleContentElement = contentElement.select("div.content")[0]
                        val articleCoverElements = atricleContentElement.select("div.image-block")
                        val articleCoverElementsCount = articleCoverElements.size
                        for (i in 0 until articleCoverElementsCount) {
                            val articleCover = articleCoverElements[i]
                            val articleCoverData = ArticleCoverData()
                            val cover = ImageData()
                            cover.url = articleCover.select("img").first().attr("src")
                            articleCoverData.cover = cover
                            articleCoverData.name = articleCover.select("h3").select("a").first().text()
                            articleCoverData.author = articleCover.select("span.tips").select("a").first().text()
                            articleCoverData.time = articleCover.select("span.tips.black").select("a").first().text()
                            articleCoverData.left = i % 2 == 0
                            articleCoverData.right = i % 2 == 1
                            articleCoverDatas.add(articleCoverData)
                        }
                        homePageIndexData.articles = articleCoverDatas

                        val magazinesMoudleTitle = MoudleTitleData()
                        magazinesMoudleTitle.title = "杂志推荐"
                        magazinesMoudleTitle.more = "更多"
                        homePageIndexData.magazines_moudle_title = magazinesMoudleTitle

                        val magazineCoverDatas = ArrayList<MagazineCoverData>()
                        val magazineContentElement = contentElement.select("div.content")[1]
                        val magazineCoverElements = magazineContentElement.select("div.special-block")
                        for (articleCover in magazineCoverElements) {
                            val magazineCoverData = MagazineCoverData()
                            val cover = ImageData()
                            cover.url = articleCover.select("img").first().attr("src")
                            magazineCoverData.cover = cover
                            magazineCoverData.name = articleCover.select("h3").select("a").first().text()
                            magazineCoverData.time = articleCover.select("span.tips").first().text()
                            magazineCoverData.order = articleCover.select("span.tips.black").first().text()
                            magazineCoverDatas.add(magazineCoverData)
                        }
                        homePageIndexData.magazines = magazineCoverDatas

                        homePageBannerViewHolder!!.setData(homePageIndexData.show_img!!)
                        homePageAdapter!!.data.add(homePageIndexData.article_moudle_title!!)
                        homePageAdapter!!.data.addAll(homePageIndexData.articles!!)
                        homePageAdapter!!.data.add(homePageIndexData.magazines_moudle_title!!)
                        homePageAdapter!!.data.addAll(homePageIndexData.magazines!!)

                        recyclerView!!.visibility = View.VISIBLE
                        Log.d("okhttp-------------", document.html())
                    }

                    override fun onError(@NonNull e: Throwable) {
                        recyclerView!!.visibility = View.GONE
                        when (e) {
                            is SocketTimeoutException -> Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show()
                            is ConnectException -> Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(context, "错误" + e.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onComplete() {
                        mRefreshLayout!!.isRefreshing = false
                    }
                })
    }

    override fun onRefresh() {
        requestData()
    }

    companion object {

        val instance: HomePageFragment
            get() = HomePageFragment()
    }
}
