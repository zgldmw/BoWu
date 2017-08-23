package com.dmw.zgl.bowu.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/10 11:32
 * Update:          2017/8/10 11:32
 * Description:     BaseFragment
 */

abstract class BaseFragment : Fragment() {
    private var mContentView: View? = null
    private var mIsVisibleToUser: Boolean = false
    private var mIsFirst: Boolean = false
    private val TAG = javaClass.simpleName

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mContentView == null) {
            mContentView = inflater!!.inflate(setContentView(), container, false)
            initView(mContentView)
            mIsFirst = true
        }
        lazyLoad()
        Log.d(TAG, "onCreateView")
        return mContentView
    }

    private fun lazyLoad() {
        if (mIsFirst && mIsVisibleToUser) {
            mIsFirst = false
            setData()
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisibleToUser = if (parentFragment == null) isVisibleToUser else parentFragment.userVisibleHint && isVisibleToUser
        lazyLoad()
        Log.d(TAG, "isVisibleToUser：" + isVisibleToUser)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    @LayoutRes
    protected abstract fun setContentView(): Int

    protected abstract fun initView(contentView: View?)

    protected abstract fun setData()
}
