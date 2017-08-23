package com.dmw.zgl.bowu.ui.sort

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.View

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.api.ApiService
import com.dmw.zgl.bowu.base.BaseFragment
import com.dmw.zgl.bowu.base.HttpService
import com.dmw.zgl.bowu.model.ArticleTypeCategoryData

import org.jsoup.nodes.Document

import java.util.ArrayList

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 15:44
 * Update:          2017/8/8 15:44
 * Description:     SortFragment
 */

class SortFragment : BaseFragment() {
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var sortFragmentAdapter: SortFragmentAdapter? = null

    override fun setContentView(): Int {
        return R.layout.fragment_sort
    }

    override fun initView(contentView: View?) {
        swipeRefreshLayout = contentView!!.findViewById(R.id.refreshlayout)
        swipeRefreshLayout!!.isEnabled = false
        val viewPager = contentView.findViewById<ViewPager>(R.id.viewpager)
        sortFragmentAdapter = SortFragmentAdapter(childFragmentManager)
        viewPager.adapter = sortFragmentAdapter

        val tabLayout = contentView.findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun setData() {
        requestTypeData()
    }

    private fun requestTypeData() {
        val apiService = HttpService.initRetrofit()!!.create(ApiService::class.java)
        apiService.sortType.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Document>() {

                    override fun onStart() {
                        super.onStart()
                        swipeRefreshLayout!!.isRefreshing = true
                    }

                    override fun onNext(@NonNull document: Document) {
                        val typeCategoryDatas = ArrayList<ArticleTypeCategoryData>()
                        val types = document.select("body > div.wpr").first().select("a")
                        for (element in types) {
                            val typeCategoryData = ArticleTypeCategoryData()
                            typeCategoryData.type = element.text()
                            typeCategoryData.url = element.attr("href")
                            typeCategoryDatas.add(typeCategoryData)
                        }
                        sortFragmentAdapter!!.addCategoryDatas(typeCategoryDatas)

                        Log.d("SortFragment", types.html())
                    }

                    override fun onError(@NonNull e: Throwable) {

                    }

                    override fun onComplete() {
                        swipeRefreshLayout!!.isRefreshing = false
                    }
                })
    }

    companion object {

        val instance: SortFragment
            get() = SortFragment()
    }


}
