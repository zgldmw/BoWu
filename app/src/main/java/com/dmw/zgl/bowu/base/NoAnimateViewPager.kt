package com.dmw.zgl.bowu.base

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

/**
 * Author:          dmw
 * Email:           2559531803@qq.com
 * Create:          22:50
 * Update:          22:50
 * Description:     BoWu
 */

class NoAnimateViewPager : ViewPager {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    //    @Override
    //    public boolean onTouchEvent(MotionEvent ev) {
    //        return false;
    //    }
    //
    //    @Override
    //    public boolean onInterceptTouchEvent(MotionEvent ev) {
    //        return false;
    //    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }
}
