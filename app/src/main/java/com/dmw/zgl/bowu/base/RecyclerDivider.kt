package com.dmw.zgl.bowu.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.LinearLayout

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/14 11:00
 * Update:          2017/8/14 11:00
 * Description:     RecyclerDivider
 */
class RecyclerDivider(context: Context) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null
    private var mOrientation: Int = 0
    private var spanCount = 1
    private val mBounds = Rect()

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    fun setDrawable(drawable: Drawable) {
        mDivider = drawable
    }

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State?) {
        val layoutManager = parent.layoutManager
        if (layoutManager != null) {
            if (layoutManager is StaggeredGridLayoutManager) {
                mOrientation = layoutManager.orientation
                spanCount = layoutManager.spanCount
                val childCount = parent.childCount
                initItemOffsets(childCount, outRect)
            } else if (layoutManager is GridLayoutManager) {
                mOrientation = layoutManager.orientation
                spanCount = layoutManager.spanCount
                val childCount = parent.childCount
                initItemOffsets(childCount, outRect)
            } else {
                val linearLayoutManager = layoutManager as LinearLayoutManager
                mOrientation = linearLayoutManager.orientation
                if (mOrientation == VERTICAL) {
                    outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
                } else {
                    outRect.set(0, 0, mDivider!!.intrinsicWidth, 0)
                }
            }
        }

    }

    private fun initItemOffsets(childCount: Int, outRect: Rect) {
        val rowLastOne = spanCount - 1
        val dividerWidth = mDivider!!.intrinsicWidth
        val dividerHeight = mDivider!!.intrinsicHeight
        for (i in 0 until childCount) {
            if (mOrientation == VERTICAL) {
                if (i % spanCount == rowLastOne) {
                    outRect.set(dividerWidth, dividerHeight, dividerWidth, 0)
                } else {
                    outRect.set(dividerWidth, dividerHeight, 0, 0)
                }

            } else {
                if (i % spanCount == rowLastOne) {
                    outRect.set(dividerWidth, dividerHeight, 0, dividerHeight)
                } else {
                    outRect.set(dividerWidth, dividerHeight, 0, 0)
                }
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        val layoutManager = parent.layoutManager
        if (layoutManager != null) {
            if (layoutManager is StaggeredGridLayoutManager) {
                if (mOrientation == VERTICAL) {
                    drawVertical(c, parent)
                } else {

                }
            } else if (layoutManager is GridLayoutManager) {
                if (mOrientation == VERTICAL) {
                    drawVertical(c, parent)
                } else {

                }
            } else {
                if (mOrientation == VERTICAL) {
                    drawVertical(c, parent)
                } else {
                    drawHorizontal(c, parent)
                }
            }
        }
    }

    //绘制纵向 item 分割线
    @SuppressLint("NewApi")
    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + Math.round(child.translationY)
            val top = bottom - mDivider!!.intrinsicHeight
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)
        }
        canvas.restore()
    }

    //绘制横向 item 分割线
    @SuppressLint("NewApi")
    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int
        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top,
                    parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.layoutManager.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + Math.round(child.translationX)
            val left = right - mDivider!!.intrinsicWidth
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)
        }
        canvas.restore()
    }

    @SuppressLint("NewApi")
    private fun drawGridVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = (parent.width - parent.paddingRight) / spanCount
            canvas.clipRect(left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width / spanCount
        }

        val rowLastOne = spanCount - 1
        val childCount = parent.childCount
        val dividerWidth = mDivider!!.intrinsicWidth
        val dividerHeight = mDivider!!.intrinsicHeight
        for (i in 0 until childCount) {
            val indexInRow = i % spanCount
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            mDivider!!.setBounds(indexInRow * right, mBounds.top + Math.round(child.translationY), indexInRow * right + dividerWidth, mBounds.bottom + Math.round(child.translationY))
            mDivider!!.draw(canvas)
            mDivider!!.setBounds(indexInRow * right, mBounds.top + Math.round(child.translationY), (indexInRow + 1) * right, mBounds.top + Math.round(child.translationY) + dividerHeight)
            mDivider!!.draw(canvas)
            if (indexInRow == rowLastOne) {
                mDivider!!.setBounds((indexInRow + 1) * right - dividerWidth, mBounds.top + Math.round(child.translationY), (rowLastOne + 1) * right, mBounds.bottom + Math.round(child.translationY))
                mDivider!!.draw(canvas)
            }
        }
        canvas.restore()
    }

    @SuppressLint("NewApi")
    private fun drawGridHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int
        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top,
                    parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.layoutManager.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + Math.round(child.translationX)
            val left = right - mDivider!!.intrinsicWidth
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)
        }
        canvas.restore()
    }

    companion object {
        private val HORIZONTAL = LinearLayout.HORIZONTAL
        private val VERTICAL = LinearLayout.VERTICAL

        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}
