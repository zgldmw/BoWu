package com.dmw.zgl.bowu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.model.ImageData;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 16:00
 * Update:          2017/8/8 16:00
 * Description:     HomePageBannerViewHolder
 */

public class HomePageBannerViewHolder {
    private View mWholeView;

    private SimpleDraweeView img;
    private TextView name;
    private TextView desc;

    public HomePageBannerViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.layout_homepage_banner, parent, false);
        img = mWholeView.findViewById(R.id.img);
        name = mWholeView.findViewById(R.id.name);
        desc = mWholeView.findViewById(R.id.desc);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(ImageData imageData) {
        img.setImageURI(imageData.url);
        name.setText(imageData.name);
        desc.setText(imageData.desc);
    }

}
