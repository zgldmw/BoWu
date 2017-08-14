package com.dmw.zgl.bowu.ui.altas;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.api.ApiService;
import com.dmw.zgl.bowu.base.BaseFragment;
import com.dmw.zgl.bowu.base.HttpService;
import com.dmw.zgl.bowu.model.AltasYearData;
import com.dmw.zgl.bowu.model.ImageData;
import com.dmw.zgl.bowu.model.MagazineCoverData;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 15:45
 * Update:          2017/8/8 15:45
 * Description:     AltasFragment
 */

public class AltasFragment extends BaseFragment {
    private int currentPage;
    private AltasAdapter altasAdapter;

    public static AltasFragment getInstance() {
        AltasFragment altasFragment = new AltasFragment();
        return altasFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_altas;
    }

    @Override
    protected void initView(View contentView) {
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        altasAdapter = new AltasAdapter();
        recyclerView.setAdapter(altasAdapter);
    }

    @Override
    protected void setData() {
        currentPage = Calendar.getInstance().get(Calendar.YEAR);
        ApiService apiService = HttpService.getInstance().create(ApiService.class);
        apiService.getAltaList(currentPage).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Document>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                        Log.d("AltasFragment", "开始请求");
                    }

                    @Override
                    public void onNext(@NonNull Document document) {
                        Element content = document.select("div.wpr").get(3).select("div.content").first();
                        ArrayList<Object> objects = new ArrayList<>();
                        Elements photoList = content.select("ul.photo-list");
                        AltasYearData altasYearData = new AltasYearData();
                        altasYearData.year = currentPage + "";
                        objects.add(altasYearData.year);
                        for (Element element : photoList) {
                            ArrayList<ImageData> imageDatas = new ArrayList<>();
                            Elements imgs = element.select("li");
                            for (Element imgData : imgs) {
                                if (imgData.hasClass("header")) {
                                    MagazineCoverData magazineCoverData = new MagazineCoverData();
                                    ImageData image = new ImageData();
                                    Element img = imgData.select("div.img").first().select("img").first();
                                    image.url = img.attr("src");
                                    magazineCoverData.cover = image;
                                    Element detail = imgData.select("div.detail").first();
                                    magazineCoverData.link_url = detail.select("a").first().attr("href");
                                    magazineCoverData.name = detail.select("h1").first().text();
                                    magazineCoverData.order = detail.select("span.tips").first().text();
                                    altasYearData.magazineCover = magazineCoverData;
                                    objects.add(altasYearData.magazineCover);
                                } else if (imgData.hasClass("more")) {
                                    ImageData imageData = new ImageData();
                                    Element more = imgData.select("span > a").first();
                                    imageData.name = more.text();
                                    imageData.desc = more.attr("href");
                                    imageDatas.add(imageData);
                                } else {
                                    ImageData imageData = new ImageData();
                                    imageData.url = imgData.select("img").first().attr("src");
                                    imageData.name = imgData.select("div.mask").first().text();
                                    imageDatas.add(imageData);
                                }
                            }
                            altasYearData.images = imageDatas;
                            objects.addAll(altasYearData.images);
                            Object o = new Object();
                            objects.add(o);
                        }

                        altasAdapter.addHomePage(objects);
                        Log.d("AltasFragment", document.html());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
