package com.dmw.zgl.bowu.base

import org.jsoup.nodes.Document

import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer
import java.nio.charset.Charset

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 11:38
 * Update:          2017/8/8 11:38
 * Description:     HtmlRquestBodyConverter
 */

class HtmlRquestBodyConverter : Converter<Document, RequestBody> {

    @Throws(IOException::class)
    override fun convert(value: Document): RequestBody {
        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        writer.write(value.html())
        writer.close()
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
    }

    companion object {
        val MEDIA_TYPE = MediaType.parse("text/html; charset=utf-8")
        private val UTF_8 = Charset.forName("UTF-8")
    }
}
