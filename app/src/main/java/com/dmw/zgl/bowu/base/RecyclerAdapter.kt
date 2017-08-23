package com.dmw.zgl.bowu.base

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 16:25
 * Update:          2017/8/8 16:25
 * Description:     RecyclerAdapter
 */

abstract class RecyclerAdapter : RecyclerView.Adapter<RecyclerViewHolder>() {
    private val mHeaderViews = SparseArrayCompat<View>()
    private val mFooterViews = SparseArrayCompat<View>()
    private val mDatas = ArrayList<Any>()

    private fun isHeaderViewPos(position: Int): Boolean {
        return position < headersCount
    }

    private fun isFooterViewPos(position: Int): Boolean {
        return position >= headersCount + mDatas.size
    }


    fun addHeaderView(view: View) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view)
    }

    fun addFooterView(view: View) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view)
    }

    private val headersCount: Int
        get() = mHeaderViews.size()

    private val footersCount: Int
        get() = mFooterViews.size()

    fun <T : Any> addHomePage(data: List<T>?) {
        mDatas.clear()
        if (data != null) {
            this.mDatas.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun <T : Any> addNextPage(data: List<T>?) {
        if (data != null) {
            val itemCount = data.size
            val postionStart = headersCount + mDatas.size
            this.mDatas.addAll(data)
            notifyItemRangeInserted(postionStart, itemCount)
        }
    }

    val data: ArrayList<Any>
        get() = mDatas

    fun getData(position: Int): Any {
        return mDatas[position - headersCount]
    }

    val isEmpty: Boolean
        get() = mDatas.isEmpty()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        val layoutManager = recyclerView!!.layoutManager
        if (layoutManager is GridLayoutManager) {

            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (isHeaderViewPos(position) || isFooterViewPos(position) || isFullSpan(position)) {
                        layoutManager.spanCount
                    } else 1
                }
            }
            layoutManager.spanCount = layoutManager.spanCount
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder?) {
        val lp = holder!!.itemView.layoutParams
        if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
            val position = holder.layoutPosition
            if (isHeaderViewPos(position) || isFooterViewPos(position) || isFullSpan(position)) {
                lp.isFullSpan = true
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewHolder {
        if (mHeaderViews.get(viewType) != null) {
            return RecyclerViewHolder(mHeaderViews.get(viewType))

        } else if (mFooterViews.get(viewType) != null) {
            return RecyclerViewHolder(mFooterViews.get(viewType))
        }
        return onCreateHolder(viewGroup, viewType)
    }

    protected abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder

    override fun onBindViewHolder(viewHolder: RecyclerViewHolder, position: Int) {
        if (isHeaderViewPos(position)) {
            return
        }
        if (isFooterViewPos(position)) {
            return
        }
        onBindHolder(viewHolder, position)
    }

    protected abstract fun onBindHolder(viewHolder: RecyclerViewHolder, position: Int)

    protected abstract fun isFullSpan(position: Int): Boolean

    override fun getItemCount(): Int {
        return headersCount + footersCount + mDatas.size
    }

    override fun getItemViewType(position: Int): Int {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position)
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - headersCount - mDatas.size)
        }
        return getItemType(position)
    }

    protected abstract fun getItemType(position: Int): Int

    companion object {

        private val BASE_ITEM_TYPE_HEADER = 100000
        private val BASE_ITEM_TYPE_FOOTER = 200000
    }

}
