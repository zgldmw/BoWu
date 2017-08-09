package com.dmw.zgl.bowu.base;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/8 11:38
 * Update:          2017/8/8 11:38
 * Description:     HtmlRquestBodyConverter
 */

public class HtmlRquestBodyConverter implements Converter<Document, RequestBody> {
    public static final MediaType MEDIA_TYPE = MediaType.parse("text/html; charset=utf-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");


    @Override
    public RequestBody convert(Document value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        writer.write(value.html());
        writer.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}
