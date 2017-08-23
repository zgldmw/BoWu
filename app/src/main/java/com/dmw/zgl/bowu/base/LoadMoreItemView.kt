package com.dmw.zgl.bowu.base

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

import com.dmw.zgl.bowu.R

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 14:10
 * Update:          2017/8/11 14:10
 * Description:     LoadMoreItemView
 */

class LoadMoreItemView(private val mRecyclerView: RecyclerView, private val mLoadMoreHandler: LoadMoreHandler) {
    private val mPreNumLoad = 3
    private var lastPosition: Int = 0
    private var lastPositions: IntArray? = null
    private val mLoadMoreView: View = LayoutInflater.from(mRecyclerView.context).inflate(R.layout.layout_load_more, mRecyclerView, false)

    private val progressBar: ProgressBar
    private val loadInfo: TextView

    init {
        progressBar = mLoadMoreView.findViewById(R.id.progress)
        loadInfo = mLoadMoreView.findViewById(R.id.load_more_info)
        initLoadMoreView()
    }

    private fun initLoadMoreView() {
        val adapter = mRecyclerView.adapter
        if (adapter != null && adapter is RecyclerAdapter) {
            adapter.addFooterView(mLoadMoreView)
            mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = mRecyclerView.layoutManager
                    when (layoutManager) {
                        is GridLayoutManager -> lastPosition = layoutManager.findLastVisibleItemPosition()
                        is LinearLayoutManager -> lastPosition = layoutManager.findLastVisibleItemPosition()
                        is StaggeredGridLayoutManager -> {
                            if (lastPositions == null) {
                                lastPositions = IntArray(layoutManager.spanCount)
                            }
                            layoutManager.findLastVisibleItemPositions(lastPositions)
                            lastPosition = lastPositions!!.max()!!
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val layoutManager = recyclerView!!.layoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount

                    if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition >= totalItemCount - 1 - mPreNumLoad) {
                        if (mLoadMoreHandler.isCanLoadMore) {
                            progressBar.visibility = View.VISIBLE
                            loadInfo.setText(R.string.load_more)

                            mLoadMoreHandler.loadMore()
                        } else {
                            progressBar.visibility = View.GONE
                            loadInfo.setText(R.string.no_more_data)
                        }
                    }
                }
            })
        }
    }
}
