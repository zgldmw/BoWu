package com.dmw.zgl.bowu.ui.magzines;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.api.ApiService;
import com.dmw.zgl.bowu.base.BaseFragment;
import com.dmw.zgl.bowu.base.HttpService;
import com.dmw.zgl.bowu.model.ImageData;
import com.dmw.zgl.bowu.model.MagazineCoverData;
import com.dmw.zgl.bowu.model.MagzinesSelectYearData;

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
 * Description:     MagzinesFragment
 */

public class MagzinesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, MagzinesSelectYearViewHolder.OnSelectYearListener {
    private SwipeRefreshLayout refreshLayout;
    private MagzinesAdapter magzinesAdapter;
    private MagzinesSelectYearViewHolder magzinesSelectYearViewHolder;
    private int currentPage;

    public static MagzinesFragment getInstance() {
        MagzinesFragment magzinesFragment = new MagzinesFragment();
        return magzinesFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_magzines;
    }

    @Override
    protected void initView(View contentView) {
        refreshLayout = contentView.findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        magzinesAdapter = new MagzinesAdapter();
        recyclerView.setAdapter(magzinesAdapter);
        magzinesSelectYearViewHolder = new MagzinesSelectYearViewHolder(recyclerView);
        magzinesSelectYearViewHolder.setOnSelectYearListener(this);
        magzinesAdapter.addHeaderView(magzinesSelectYearViewHolder.getWholeView());
    }

    @Override
    protected void setData() {
        currentPage = Calendar.getInstance().get(Calendar.YEAR);
        requestData();
    }

    private void requestData() {
        ApiService apiService = HttpService.getInstance().create(ApiService.class);
        apiService.getMagzines(currentPage).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Document>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                        refreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onNext(@NonNull Document document) {
                        Elements years = document.select("body > div.wpr").first().select("li");
                        ArrayList<MagzinesSelectYearData> magzinesSelectYearDatas = new ArrayList<>();
                        for (Element element : years) {
                            MagzinesSelectYearData magzinesSelectYearData = new MagzinesSelectYearData();
                            magzinesSelectYearData.active = element.hasClass("active");
                            magzinesSelectYearData.year = element.text();
                            magzinesSelectYearDatas.add(magzinesSelectYearData);
                        }
                        magzinesSelectYearViewHolder.setData(magzinesSelectYearDatas);

                        ArrayList<MagazineCoverData> magazineCoverDatas = new ArrayList<>();
                        Elements magzines = document.select("body > div.wpr").get(1).select("li");
                        int count = magzines.size();
                        for (int i = 0; i < count; i++) {
                            Element element = magzines.get(i);
                            MagazineCoverData magazineCoverData = new MagazineCoverData();
                            ImageData imageData = new ImageData();
                            Element img = element.select("div.img").first();
                            imageData.url = img.select("img").first().attr("src");
                            magazineCoverData.cover = imageData;
                            magazineCoverData.link_url = img.select("a").first().attr("href");
                            Element detail = element.select("div.detail").first();
                            magazineCoverData.name = detail.select("a").first().text();
                            magazineCoverData.order = detail.select("p").first().text();
                            magazineCoverData.left = (i % 2 == 0);
                            magazineCoverData.right = (i % 2 == 1);
                            magazineCoverDatas.add(magazineCoverData);
                        }
                        magzinesAdapter.addHomePage(magazineCoverDatas);
                        Log.d("MagzinesFragment", document.html());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("MagzinesFragment", "onComplete");
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onSelectYear(int year) {
        currentPage = year;
        requestData();
    }
}
