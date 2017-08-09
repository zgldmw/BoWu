package com.dmw.zgl.bowu.base;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/9 18:18
 * Update:          2017/8/9 18:18
 * Description:     HttpManager
 */

public class HttpService {
    private volatile static Retrofit INSTANCE; //构造方法私有

    public static Retrofit getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpService.class) {
                if (INSTANCE == null) {
                    INSTANCE = initRetrofit();
                }
            }
        }
        return INSTANCE;
    }

    private static Retrofit initRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("OkHttp-----------------", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(HtmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.dili360.com/")
                .client(okHttpClient)
                .build();

        return retrofit;
    }
}
