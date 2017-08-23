package com.dmw.zgl.bowu.ui.sort

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.api.ApiService
import com.dmw.zgl.bowu.base.BaseFragment
import com.dmw.zgl.bowu.base.HttpService
import com.dmw.zgl.bowu.base.LoadMoreHandler
import com.dmw.zgl.bowu.base.LoadMoreItemView
import com.dmw.zgl.bowu.model.ArticleCoverData
import com.dmw.zgl.bowu.model.ImageData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.jsoup.nodes.Document
import java.util.*

/**
 * Author:          dmw
 * Email:           2559531803@qq.com
 * Create:          21:19
 * Update:          21:19
 * Description:     BoWu
 */

class ArticleListFragment : BaseFragment(), LoadMoreHandler {
    private var url: String? = null
    private var totalPage: Int = 0
    private var currentPage = 1
    private var isLoading: Boolean = false
    private var articleListAdapter: ArticleListAdapter? = null

    override fun setContentView(): Int {
        return R.layout.fragment_article_list
    }

    override fun initView(contentView: View?) {
        val recyclerView = contentView!!.findViewById<RecyclerView>(R.id.recyclerview)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        articleListAdapter = ArticleListAdapter()
        recyclerView.adapter = articleListAdapter

        LoadMoreItemView(recyclerView, this)
    }

    override fun setData() {
        url = arguments.getString("url")
        if (url != null) {
            url = url!!.split(".htm".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        }
        requestData()
    }

    private fun requestData() {
        val apiService = HttpService.initRetrofit()!!.create(ApiService::class.java)
        apiService.getArticleList("$url/$currentPage.htm").subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Document>() {
                    override fun onStart() {
                        super.onStart()
                        isLoading = true
                    }

                    override fun onNext(@NonNull document: Document) {
                        isLoading = false
                        val content = document.select("body > div.wpr")[1]

                        val pages = content.select("div.pagination").first().select("li")
                        totalPage = if (pages.last().hasText()) Integer.parseInt(pages.last().text()) else Integer.parseInt(pages[pages.size - 2].text())
                        val activePage = Integer.parseInt(pages.select(".active").first().text())
                        if (currentPage != activePage) {
                            return
                        }

                        val articleCoverDatas = ArrayList<ArticleCoverData>()
                        val articleList = content.select("ul.article-list > *")
                        for (element in articleList) {
                            val articleCoverData = ArticleCoverData()
                            val imageData = ImageData()
                            val img = element.select("div.img > img").first()
                            imageData.url = if (img == null) "" else img.attr("src")
                            articleCoverData.cover = imageData
                            articleCoverData.is_free = !element.select("div.img > div.icon-free-red").isEmpty()
                            articleCoverData.name = element.select("div.detail > a").select("h3").first().text()
                            val textElements = element.select("div.detail > p")
                            articleCoverData.desc = textElements.first().text()
                            val tagElements = textElements.last().select("a")
                            val author = tagElements.select("[href*=/author/]").first()
                            articleCoverData.author = if (author == null) "" else author.text()
                            val time = tagElements.select("[href*=/detail/]").first()
                            articleCoverData.time = if (time == null) "" else time.text()
                            articleCoverDatas.add(articleCoverData)
                        }
                        if (currentPage == 1) {
                            articleListAdapter!!.addHomePage(articleCoverDatas)
                        } else {
                            articleListAdapter!!.addNextPage(articleCoverDatas)
                        }
                        currentPage++

                        Log.d("ArticleListFragment", document.html())
                    }

                    override fun onError(@NonNull e: Throwable) {
                        isLoading = false
                    }

                    override fun onComplete() {
                        isLoading = false
                    }
                })
    }

    override val isCanLoadMore = totalPage > currentPage

    override fun loadMore() {
        if (!isLoading) {
            requestData()
        }
    }

    companion object {

        fun getInstance(url: String): ArticleListFragment {
            val articleListFragment = ArticleListFragment()
            val args = Bundle()
            args.putString("url", url)
            articleListFragment.arguments = args

            return articleListFragment
        }
    }
}
