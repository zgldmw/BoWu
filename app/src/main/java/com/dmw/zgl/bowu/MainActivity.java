package com.dmw.zgl.bowu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.dmw.zgl.bowu.base.HttpService;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImagePipelineConfig imagePipelineConfig = HttpService.initPipeline(this);
        Fresco.initialize(this, imagePipelineConfig);

        initView();
    }

    private void initView() {
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, null);
        HomeActivityFragmentAdapter fragmentAdapter = new HomeActivityFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
