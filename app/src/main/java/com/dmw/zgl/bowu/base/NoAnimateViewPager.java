package com.dmw.zgl.bowu.base;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Author:          dmw
 * Email:           2559531803@qq.com
 * Create:          22:50
 * Update:          22:50
 * Description:     BoWu
 */

public class NoAnimateViewPager extends ViewPager {
    public NoAnimateViewPager(Context context) {
        super(context);
    }

    public NoAnimateViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return false;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return false;
//    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}
