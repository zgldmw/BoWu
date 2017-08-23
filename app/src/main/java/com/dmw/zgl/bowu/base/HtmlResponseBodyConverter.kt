package com.dmw.zgl.bowu.base

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import java.io.IOException

import okhttp3.ResponseBody
import retrofit2.Converter

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 11:34
 * Update:          2017/8/8 11:34
 * Description:     HtmlResponseBodyConverter
 */

class HtmlResponseBodyConverter : Converter<ResponseBody, Document> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): Document {
        value.use {
            val html = value.string()
            return Jsoup.parse(html)
        }
    }
}
