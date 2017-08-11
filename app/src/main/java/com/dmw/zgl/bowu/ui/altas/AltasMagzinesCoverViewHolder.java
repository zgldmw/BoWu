package com.dmw.zgl.bowu.ui.altas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.model.MagazineCoverData;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/11 17:04
 * Update:          2017/8/11 17:04
 * Description:     AltasMagzinesCoverViewHolder
 */

public class AltasMagzinesCoverViewHolder {
    private View mWholeView;

    private SimpleDraweeView cover;
    private TextView title;
    private TextView order;

    public AltasMagzinesCoverViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        mWholeView = LayoutInflater.from(context).inflate(R.layout.item_altas_magzines_cover, parent, false);
        cover = mWholeView.findViewById(R.id.cover);
        title = mWholeView.findViewById(R.id.title);
        order = mWholeView.findViewById(R.id.order);
    }

    public View getWholeView() {
        return mWholeView;
    }

    public void setData(MagazineCoverData magazineCoverData) {
        cover.setImageURI(magazineCoverData.cover.url);
        title.setText(magazineCoverData.name);
        order.setText(magazineCoverData.order);
    }
}
