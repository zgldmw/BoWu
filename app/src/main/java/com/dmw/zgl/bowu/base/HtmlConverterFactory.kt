package com.dmw.zgl.bowu.base

import java.lang.reflect.Type

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 11:15
 * Update:          2017/8/8 11:15
 * Description:     HtmlConverterFactory
 */

class HtmlConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return HtmlResponseBodyConverter()
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        return HtmlRquestBodyConverter()
    }

    companion object {

        fun create(): HtmlConverterFactory {
            return HtmlConverterFactory()
        }
    }
}
