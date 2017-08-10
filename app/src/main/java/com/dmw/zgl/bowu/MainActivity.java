package com.dmw.zgl.bowu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fresco.initialize(this);

        initView();
    }

    private void initView() {
        ViewPager viewPager = findViewById(R.id.viewpager);
        HomeActivityFragmentAdapter fragmentAdapter = new HomeActivityFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
