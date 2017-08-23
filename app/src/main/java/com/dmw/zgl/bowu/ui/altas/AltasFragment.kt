package com.dmw.zgl.bowu.ui.altas

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.api.ApiService
import com.dmw.zgl.bowu.base.BaseFragment
import com.dmw.zgl.bowu.base.HttpService
import com.dmw.zgl.bowu.base.LoadMoreHandler
import com.dmw.zgl.bowu.base.LoadMoreItemView
import com.dmw.zgl.bowu.model.AltasYearData
import com.dmw.zgl.bowu.model.ImageData
import com.dmw.zgl.bowu.model.MagazineCoverData

import org.jsoup.nodes.Document

import java.util.ArrayList
import java.util.Calendar

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 15:45
 * Update:          2017/8/8 15:45
 * Description:     AltasFragment
 */

class AltasFragment : BaseFragment(), LoadMoreHandler {
    private var currentPage: Int = 0
    private var altasAdapter: AltasAdapter? = null
    private var isLoading: Boolean = false

    override fun setContentView(): Int {
        return R.layout.fragment_altas
    }

    override fun initView(contentView: View?) {
        val recyclerView = contentView!!.findViewById<RecyclerView>(R.id.recyclerview)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        altasAdapter = AltasAdapter()
        recyclerView.adapter = altasAdapter
        LoadMoreItemView(recyclerView, this)
    }

    override fun setData() {
        currentPage = Calendar.getInstance().get(Calendar.YEAR)
        requestData()
    }

    private fun requestData() {
        val apiService = HttpService.initRetrofit()!!.create(ApiService::class.java)
        apiService.getAltaList(currentPage).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Document>() {

                    override fun onStart() {
                        super.onStart()
                        isLoading = true
                        Log.d("AltasFragment", "开始请求")
                    }

                    override fun onNext(@NonNull document: Document) {
                        isLoading = false
                        val activeYear = document.select("div.wpr")[2].select("div.middle.jsShow").first().text()
                        val activePage = Integer.parseInt(activeYear)
                        if (activePage != currentPage) {
                            return
                        }

                        val content = document.select("div.wpr")[3].select("div.content").first()

                        val objects = ArrayList<Any>()
                        val photoList = content.select("ul.photo-list")
                        val altasYearData = AltasYearData()
                        altasYearData.year = currentPage.toString() + ""
                        objects.add(altasYearData.year!!)
                        for (element in photoList) {
                            val imageDatas = ArrayList<ImageData>()
                            val imgs = element.select("li")
                            for (imgData in imgs) {
                                when {
                                    imgData.hasClass("header") -> {
                                        val magazineCoverData = MagazineCoverData()
                                        val image = ImageData()
                                        val img = imgData.select("div.img").first().select("img").first()
                                        image.url = img.attr("src")
                                        magazineCoverData.cover = image
                                        val detail = imgData.select("div.detail").first()
                                        magazineCoverData.link_url = detail.select("a").first().attr("href")
                                        magazineCoverData.name = detail.select("h1").first().text()
                                        magazineCoverData.order = detail.select("span.tips").first().text()
                                        altasYearData.magazineCover = magazineCoverData
                                        objects.add(altasYearData.magazineCover!!)
                                    }
                                    imgData.hasClass("more") -> {
                                        val imageData = ImageData()
                                        val more = imgData.select("span > a").first()
                                        imageData.name = more.text()
                                        imageData.desc = more.attr("href")
                                        imageDatas.add(imageData)
                                    }
                                    else -> {
                                        val imageData = ImageData()
                                        imageData.url = imgData.select("img").first().attr("src")
                                        imageData.name = imgData.select("div.mask").first().text()
                                        imageDatas.add(imageData)
                                    }
                                }
                            }
                            altasYearData.images = imageDatas
                            objects.addAll(altasYearData.images!!)
                            val o = Any()
                            objects.add(o)
                        }

                        if (currentPage == Calendar.getInstance().get(Calendar.YEAR)) {
                            altasAdapter!!.addHomePage(objects)
                        } else {
                            altasAdapter!!.addNextPage(objects)
                        }

                        currentPage--
                        Log.d("AltasFragment", document.html())
                    }

                    override fun onError(@NonNull e: Throwable) {
                        isLoading = false
                    }

                    override fun onComplete() {
                        isLoading = false
                    }
                })
    }

    override val isCanLoadMore = currentPage >= 2014

    override fun loadMore() {
        if (!isLoading) {
            requestData()
        }
    }

    companion object {

        val instance: AltasFragment
            get() = AltasFragment()
    }
}
