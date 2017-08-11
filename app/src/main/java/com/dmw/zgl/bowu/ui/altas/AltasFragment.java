package com.dmw.zgl.bowu.ui.altas;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dmw.zgl.bowu.R;
import com.dmw.zgl.bowu.api.ApiService;
import com.dmw.zgl.bowu.base.BaseFragment;
import com.dmw.zgl.bowu.base.HttpService;

import org.jsoup.nodes.Document;

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
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        AltasAdapter altasAdapter = new AltasAdapter();
        recyclerView.setAdapter(altasAdapter);
    }

    @Override
    protected void setData() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        ApiService apiService = HttpService.getInstance().create(ApiService.class);
        apiService.getAltaList(year).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Document>() {

                    @Override
                    public void onNext(@NonNull Document document) {
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
