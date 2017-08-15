package com.dmw.zgl.bowu.ui.altas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.base.FrescoUtils;
import com.dmw.zgl.bowu.model.ImageData;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.flexbox.FlexboxLayoutManager;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 17:35
 * Update:          2017/8/11 17:35
 * Description:     AltaItemViewholder
 */

public class AltaItemViewholder implements View.OnTouchListener {
    private View mWholeView;

    private SimpleDraweeView img;
    private TextView hover;

    public AltaItemViewholder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.item_alta, parent, false);
        img = mWholeView.findViewById(R.id.img);
        hover = mWholeView.findViewById(R.id.hover);

        mWholeView.setOnTouchListener(this);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(ImageData imageData) {
        img.setAspectRatio(1.7f);
        FrescoUtils.displayImgAspectRatio(img, imageData.url);
        hover.setText(imageData.name);
        if (TextUtils.isEmpty(imageData.url)) {
            hover.setBackgroundResource(0);
            hover.setVisibility(View.VISIBLE);
        } else {
            hover.setBackgroundResource(R.color.black_alpha);
            hover.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                hover.setVisibility(View.VISIBLE);
                break;
            case MotionEvent.ACTION_UP:
                hover.setVisibility(View.GONE);
                break;
            case MotionEvent.ACTION_CANCEL:
                hover.setVisibility(View.GONE);
                break;
        }
        return true;
    }
}
