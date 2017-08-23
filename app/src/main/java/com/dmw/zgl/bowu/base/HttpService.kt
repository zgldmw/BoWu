package com.dmw.zgl.bowu.base

import android.content.Context
import android.util.Log

import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.core.ImagePipelineConfig

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/9 18:18
 * Update:          2017/8/9 18:18
 * Description:     HttpManager
 */

object HttpService {
    @Volatile private var RETROFITINSTANCE: Retrofit? = null //构造方法私有
    @Volatile private var OKHTTPINSTANCE: OkHttpClient? = null
    @Volatile private var PIPELINEINSTANCE: ImagePipelineConfig? = null

    fun initRetrofit(): Retrofit? {
        if (RETROFITINSTANCE == null) {
            synchronized(HttpService::class.java) {
                if (RETROFITINSTANCE == null) {
                    RETROFITINSTANCE = Retrofit.Builder()
                            .addConverterFactory(HtmlConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl("http://www.dili360.com/")
                            .client(initOkHttpClient()!!)
                            .build()
                }
            }
        }
        return RETROFITINSTANCE
    }

    private fun initOkHttpClient(): OkHttpClient? {
        if (OKHTTPINSTANCE == null) {
            synchronized(HttpService::class.java) {
                if (OKHTTPINSTANCE == null) {
                    val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.e("OkHttp-----------------", message) })
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                    OKHTTPINSTANCE = OkHttpClient()
                    OKHTTPINSTANCE!!.newBuilder()
                            .addInterceptor(loggingInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .build()
                }
            }
        }

        return OKHTTPINSTANCE
    }

    fun initPipeline(context: Context): ImagePipelineConfig? {
        if (PIPELINEINSTANCE == null) {
            synchronized(HttpService::class.java) {
                if (PIPELINEINSTANCE == null) {
                    PIPELINEINSTANCE = OkHttpImagePipelineConfigFactory
                            .newBuilder(context, initOkHttpClient()!!)
                            .setDownsampleEnabled(true)
                            .build()
                }
            }
        }

        return PIPELINEINSTANCE
    }
}
