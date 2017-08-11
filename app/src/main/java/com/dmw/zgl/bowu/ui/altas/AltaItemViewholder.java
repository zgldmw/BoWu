package com.dmw.zgl.bowu.ui.altas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.model.ImageData;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 17:35
 * Update:          2017/8/11 17:35
 * Description:     AltaItemViewholder
 */

public class AltaItemViewholder {
    private View mWholeView;

    private SimpleDraweeView img;
    private TextView hover;

    public AltaItemViewholder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.item_alta, parent, false);
        img = mWholeView.findViewById(R.id.img);
        hover = mWholeView.findViewById(R.id.hover);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(ImageData imageData) {
        img.setImageURI(imageData.url);
        hover.setText(imageData.desc);
    }
}