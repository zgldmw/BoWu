package com.dmw.zgl.bowu.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/14 11:00
 * Update:          2017/8/14 11:00
 * Description:     RecyclerDivider
 */
public class RecyclerDivider extends RecyclerView.ItemDecoration {
    private static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    private static final int VERTICAL = LinearLayout.VERTICAL;

    private Drawable mDivider;
    private int mOrientation;
    private int spanCount = 1;
    private final Rect mBounds = new Rect();

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public RecyclerDivider(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public void setDrawable(@NonNull Drawable drawable) {
        mDivider = drawable;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                mOrientation = staggeredGridLayoutManager.getOrientation();
                spanCount = staggeredGridLayoutManager.getSpanCount();
                int childCount = parent.getChildCount();
                initItemOffsets(childCount, outRect);
            } else if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                mOrientation = gridLayoutManager.getOrientation();
                spanCount = gridLayoutManager.getSpanCount();
                int childCount = parent.getChildCount();
                initItemOffsets(childCount, outRect);
            } else {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                mOrientation = linearLayoutManager.getOrientation();
                if (mOrientation == VERTICAL) {
                    outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
                } else {
                    outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
                }
            }
        }

    }

    private void initItemOffsets(int childCount, Rect outRect) {
        int rowLastOne = spanCount - 1;
        int dividerWidth = mDivider.getIntrinsicWidth();
        int dividerHeight = mDivider.getIntrinsicHeight();
        for (int i = 0; i < childCount; i++) {
            if (mOrientation == VERTICAL) {
                if (i % spanCount == rowLastOne) {
                    outRect.set(dividerWidth, dividerHeight, dividerWidth, 0);
                } else {
                    outRect.set(dividerWidth, dividerHeight, 0, 0);
                }

            } else {
                if (i % spanCount == rowLastOne) {
                    outRect.set(dividerWidth, dividerHeight, 0, dividerHeight);
                } else {
                    outRect.set(dividerWidth, dividerHeight, 0, 0);
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                if (mOrientation == VERTICAL) {
                    drawVertical(c, parent);
                } else {

                }
            } else if (layoutManager instanceof GridLayoutManager) {
                if (mOrientation == VERTICAL) {
                    drawVertical(c, parent);
                } else {

                }
            } else {
                if (mOrientation == VERTICAL) {
                    drawVertical(c, parent);
                } else {
                    drawHorizontal(c, parent);
                }
            }
        }
    }

    //绘制纵向 item 分割线
    @SuppressLint("NewApi")
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    //绘制横向 item 分割线
    @SuppressLint("NewApi")
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @SuppressLint("NewApi")
    private void drawGridVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = (parent.getWidth() - parent.getPaddingRight()) / spanCount;
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth() / spanCount;
        }

        int rowLastOne = spanCount - 1;
        final int childCount = parent.getChildCount();
        int dividerWidth = mDivider.getIntrinsicWidth();
        int dividerHeight = mDivider.getIntrinsicHeight();
        for (int i = 0; i < childCount; i++) {
            int indexInRow = i % spanCount;
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            mDivider.setBounds(indexInRow * right, mBounds.top + Math.round(child.getTranslationY()), indexInRow * right + dividerWidth, mBounds.bottom + Math.round(child.getTranslationY()));
            mDivider.draw(canvas);
            mDivider.setBounds(indexInRow * right, mBounds.top + Math.round(child.getTranslationY()), (indexInRow + 1) * right, mBounds.top + Math.round(child.getTranslationY()) + dividerHeight);
            mDivider.draw(canvas);
            if (indexInRow == rowLastOne) {
                mDivider.setBounds((indexInRow + 1) * right - dividerWidth, mBounds.top + Math.round(child.getTranslationY()), (rowLastOne + 1) * right, mBounds.bottom + Math.round(child.getTranslationY()));
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    @SuppressLint("NewApi")
    private void drawGridHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }
}
