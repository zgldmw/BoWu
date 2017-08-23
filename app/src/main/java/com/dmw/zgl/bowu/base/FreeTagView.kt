package com.dmw.zgl.bowu.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

import com.dmw.zgl.bowu.R

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/10 14:50
 * Update:          2017/8/10 14:50
 * Description:     FreeTagView
 */

class FreeTagView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val path: Path = Path()
    private val textPath: Path = Path()
    private val paint: Paint = Paint()
    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private val textVOffset: Int
    private var textHOffset: Int = 0
    private val text = "免费"
    private val textSize = resources.getDimensionPixelSize(R.dimen.sp16).toFloat()
    private val textColor = resources.getColor(android.R.color.white)
    private val backColor = resources.getColor(R.color.orange)

    init {
        paint.isDither = true//设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paint.isAntiAlias = true//设置抗锯齿
        paint.strokeWidth = 5f
        paint.style = Paint.Style.FILL
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND//圆角

        textVOffset = -resources.getDimensionPixelOffset(R.dimen.px15)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = measuredWidth
        viewHeight = measuredHeight
        setMeasuredDimension(width, height)
        textHOffset = (Math.sqrt(Math.pow(viewWidth.toDouble(), 2.0) + Math.pow(viewHeight.toDouble(), 2.0)) * 0.3).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        paint.color = backColor
        path.moveTo(0f, viewHeight.toFloat())
        path.lineTo(0f, 0f)
        path.lineTo(viewWidth.toFloat(), 0f)
        path.lineTo(0f, viewHeight.toFloat())
        canvas.drawPath(path, paint)
        paint.color = textColor
        paint.textSize = textSize
        textPath.moveTo(0f, viewHeight.toFloat())
        textPath.lineTo(viewWidth.toFloat(), 0f)
        canvas.drawTextOnPath(text, textPath, textHOffset.toFloat(), textVOffset.toFloat(), paint)
        canvas.restore()
    }
}
