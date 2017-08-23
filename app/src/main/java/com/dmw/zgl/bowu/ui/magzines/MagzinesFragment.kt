package com.dmw.zgl.bowu.ui.magzines

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.api.ApiService
import com.dmw.zgl.bowu.base.BaseFragment
import com.dmw.zgl.bowu.base.HttpService
import com.dmw.zgl.bowu.model.ImageData
import com.dmw.zgl.bowu.model.MagazineCoverData
import com.dmw.zgl.bowu.model.MagzinesSelectYearData

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
 * Description:     MagzinesFragment
 */

class MagzinesFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, MagzinesSelectYearViewHolder.OnSelectYearListener {
    private var refreshLayout: SwipeRefreshLayout? = null
    private var magzinesAdapter: MagzinesAdapter? = null
    private var magzinesSelectYearViewHolder: MagzinesSelectYearViewHolder? = null
    private var currentPage: Int = 0

    override fun setContentView(): Int {
        return R.layout.fragment_magzines
    }

    override fun initView(contentView: View?) {
        refreshLayout = contentView!!.findViewById(R.id.refreshlayout)
        refreshLayout!!.setOnRefreshListener(this)
        val recyclerView = contentView.findViewById<RecyclerView>(R.id.recyclerview)
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager
        magzinesAdapter = MagzinesAdapter()
        recyclerView.adapter = magzinesAdapter
        magzinesSelectYearViewHolder = MagzinesSelectYearViewHolder(recyclerView)
        magzinesSelectYearViewHolder!!.setOnSelectYearListener(this)
        magzinesAdapter!!.addHeaderView(magzinesSelectYearViewHolder!!.wholeView)
    }

    override fun setData() {
        currentPage = Calendar.getInstance().get(Calendar.YEAR)
        requestData()
    }

    private fun requestData() {
        val apiService = HttpService.initRetrofit()!!.create(ApiService::class.java)
        apiService.getMagzines(currentPage).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Document>() {

                    override fun onStart() {
                        super.onStart()
                        refreshLayout!!.isRefreshing = true
                    }

                    override fun onNext(@NonNull document: Document) {
                        val years = document.select("body > div.wpr").first().select("li")
                        val magzinesSelectYearDatas = ArrayList<MagzinesSelectYearData>()
                        for (element in years) {
                            val magzinesSelectYearData = MagzinesSelectYearData()
                            magzinesSelectYearData.active = element.hasClass("active")
                            magzinesSelectYearData.year = element.text()
                            magzinesSelectYearDatas.add(magzinesSelectYearData)
                        }
                        magzinesSelectYearViewHolder!!.setData(magzinesSelectYearDatas)

                        val magazineCoverDatas = ArrayList<MagazineCoverData>()
                        val magzines = document.select("body > div.wpr")[1].select("li")
                        val count = magzines.size
                        for (i in 0 until count) {
                            val element = magzines[i]
                            val magazineCoverData = MagazineCoverData()
                            val imageData = ImageData()
                            val img = element.select("div.img").first()
                            imageData.url = img.select("img").first().attr("src")
                            magazineCoverData.cover = imageData
                            magazineCoverData.link_url = img.select("a").first().attr("href")
                            val detail = element.select("div.detail").first()
                            magazineCoverData.name = detail.select("a").first().text()
                            magazineCoverData.order = detail.select("p").first().text()
                            magazineCoverData.left = i % 2 == 0
                            magazineCoverData.right = i % 2 == 1
                            magazineCoverDatas.add(magazineCoverData)
                        }
                        magzinesAdapter!!.addHomePage(magazineCoverDatas)
                        Log.d("MagzinesFragment", document.html())
                    }

                    override fun onError(@NonNull e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        Log.d("MagzinesFragment", "onComplete")
                        refreshLayout!!.isRefreshing = false
                    }
                })
    }

    override fun onRefresh() {
        requestData()
    }

    override fun onSelectYear(year: Int) {
        currentPage = year
        requestData()
    }

    companion object {

        val instance: MagzinesFragment
            get() = MagzinesFragment()
    }
}
