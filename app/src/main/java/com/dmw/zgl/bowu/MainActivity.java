package com.dmw.zgl.bowu;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity {
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
        int count = fragments.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTitle[i]);
            tabSpec.setIndicator(getTabItemView(i));
            tabHost.addTab(tabSpec, fragments[i], null);
        }

    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.item_tab_view, null);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(tabTitle[index]);

        return view;
    }
}
