package com.dmw.zgl.bowu.base;

import android.content.Context;
import android.util.Log;

import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

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
    private volatile static Retrofit RETROFITINSTANCE; //构造方法私有
    private volatile static OkHttpClient OKHTTPINSTANCE;
    private volatile static ImagePipelineConfig PIPELINEINSTANCE;

    public static Retrofit getInstance() {
        if (RETROFITINSTANCE == null) {
            synchronized (HttpService.class) {
                if (RETROFITINSTANCE == null) {
                    RETROFITINSTANCE = new Retrofit.Builder()
                            .addConverterFactory(HtmlConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl("http://www.dili360.com/")
                            .client(initOkHttpClient())
                            .build();
                }
            }
        }
        return RETROFITINSTANCE;
    }

    private static OkHttpClient initOkHttpClient() {
        if (OKHTTPINSTANCE == null) {
            synchronized (HttpService.class) {
                if (OKHTTPINSTANCE == null) {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Log.e("OkHttp-----------------", message);
                        }
                    });
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    OKHTTPINSTANCE = new OkHttpClient();
                    OKHTTPINSTANCE.newBuilder()
                            .addInterceptor(loggingInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .build();
                }
            }
        }

        return OKHTTPINSTANCE;
    }

    public static ImagePipelineConfig initPipeline(Context context) {
        if (PIPELINEINSTANCE == null) {
            synchronized (HttpService.class) {
                if (PIPELINEINSTANCE == null) {
                    PIPELINEINSTANCE = OkHttpImagePipelineConfigFactory
                            .newBuilder(context, initOkHttpClient())
                            .setDownsampleEnabled(true)
                            .build();
                }
            }
        }

        return PIPELINEINSTANCE;
    }
}
