package com.dmw.zgl.bowu.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.dmw.zgl.bowu.R;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/10 14:50
 * Update:          2017/8/10 14:50
 * Description:     FreeTagView
 */

public class FreeTagView extends View {
    private Path path;
    private Path textPath;
    private Paint paint;
    private int width;
    private int height;
    private int textVOffset;
    private int textHOffset;
    private String text = "免费";

    public FreeTagView(Context context) {
        this(context, null);
    }

    public FreeTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FreeTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = new Path();
        textPath = new Path();
        paint = new Paint();
        paint.setDither(true);//设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);//圆角

        textVOffset = -getResources().getDimensionPixelOffset(R.dimen.px15);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        setMeasuredDimension(width, height);
        textHOffset = (int) (Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) * 0.3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        paint.setColor(getResources().getColor(R.color.orange));
        path.moveTo(0, height);
        path.lineTo(0, 0);
        path.lineTo(width, 0);
        path.lineTo(0, height);
        canvas.drawPath(path, paint);
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp16));
        textPath.moveTo(0, height);
        textPath.lineTo(width, 0);
        canvas.drawTextOnPath(text, textPath, textHOffset, textVOffset, paint);
        canvas.restore();
    }
}
