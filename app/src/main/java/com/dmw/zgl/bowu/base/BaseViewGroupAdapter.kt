package com.dmw.zgl.bowu.base

import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

abstract class BaseViewGroupAdapter<T : Any>(private var mContainer: ViewGroup) {

    var data = ArrayList<T>()

    protected abstract fun getView(position: Int, convertView: View?, parent: ViewGroup): View

    fun addHomeData(list: ArrayList<T>?) {
        this.data.clear()
        if (list != null) {
            this.data.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addMoreData(list: ArrayList<T>?) {
        if (list != null) {
            this.data.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addData(data: T?) {
        if (data != null) {
            this.data.add(data)
        }
        notifyDataSetChanged()
    }

    private fun notifyDataSetChanged() {
        val count = data.size
        for (i in 0 until count) {
            val mCacheView = getOldView(i)
            if (mCacheView != null) {
                getView(i, mCacheView, mContainer)
                mCacheView.visibility = View.VISIBLE
            } else {
                val view = getView(i, null, mContainer)
                mContainer.addView(view)
            }
        }

        val childCount = mContainer.childCount

        for (i in data.size until childCount) {
            mContainer.getChildAt(i).visibility = View.GONE
        }
    }

    private fun getOldView(index: Int): View? {
        return if (index < mContainer.childCount) {
            mContainer.getChildAt(index)
        } else null
    }
}
