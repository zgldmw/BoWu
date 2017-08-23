package com.dmw.zgl.bowu.ui.altas

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dmw.zgl.bowu.R
import com.dmw.zgl.bowu.base.FrescoUtils
import com.dmw.zgl.bowu.model.ImageData
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 17:35
 * Update:          2017/8/11 17:35
 * Description:     AltaItemViewholder
 */

class AltaItemViewholder(parent: ViewGroup) : View.OnTouchListener {
    val wholeView: View

    private val img: SimpleDraweeView
    private val hover: TextView

    init {
        val context = parent.context
        wholeView = LayoutInflater.from(context).inflate(R.layout.item_alta, parent, false)
        img = wholeView.findViewById(R.id.img)
        hover = wholeView.findViewById(R.id.hover)

        wholeView.setOnTouchListener(this)
    }

    fun setData(imageData: ImageData) {
        img.aspectRatio = 1.7f
        FrescoUtils.displayImgAspectRatio(img, imageData.url)
        hover.text = imageData.name
        if (TextUtils.isEmpty(imageData.url)) {
            hover.setBackgroundResource(0)
            hover.visibility = View.VISIBLE
        } else {
            hover.setBackgroundResource(R.color.black_alpha)
            hover.visibility = View.GONE
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> hover.visibility = View.VISIBLE
            MotionEvent.ACTION_UP -> hover.visibility = View.GONE
            MotionEvent.ACTION_CANCEL -> hover.visibility = View.GONE
        }
        return true
    }
}
