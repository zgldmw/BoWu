package com.dmw.zgl.bowu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.dmw.zgl.bowu.ui.altas.AltasFragment;
import com.dmw.zgl.bowu.ui.home.HomePageFragment;
import com.dmw.zgl.bowu.ui.magzines.MagzinesFragment;
import com.dmw.zgl.bowu.ui.sort.SortFragment;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends FragmentActivity {
    private LayoutInflater layoutInflater;
    private String[] tabTitle = new String[]{"首页", "分类", "图集", "杂志"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fresco.initialize(this);

        initView();
    }

    private void initView() {
        Class fragments[] = {HomePageFragment.class, SortFragment.class, AltasFragment.class, MagzinesFragment.class};

        layoutInflater = LayoutInflater.from(this);
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabHost.getTabWidget().setDividerDrawable(null);

        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTitle[i]);
            tabSpec.setIndicator(getTabItemView(i));
            tabHost.addTab(tabSpec, fragments[i], null);
        }
    }

    private View getTabItemView(int index) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(layoutParams);
        int vPadding = getResources().getDimensionPixelOffset(R.dimen.px20);
        textView.setPadding(0, vPadding, 0, vPadding);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTextColor(getResources().getColorStateList(R.drawable.selector_tab_text));
        textView.setText(tabTitle[index]);

        return textView;
    }
}
