package com.dmw.zgl.bowu.base

import android.graphics.drawable.Animatable
import android.net.Uri
import android.text.TextUtils
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequestBuilder

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/14 14:56
 * Update:          2017/8/14 14:56
 * Description:     FrescoUtils
 */

object FrescoUtils {

    fun displayImgAspectRatio(simpleDraweeView: SimpleDraweeView, url: String?) {
        val controllerListener = object : BaseControllerListener<ImageInfo>() {

            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                if (imageInfo == null) {
                    return
                }
                val aspectRatio = imageInfo.width * 1f / imageInfo.height
                simpleDraweeView.aspectRatio = aspectRatio
                //                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                //                FLog.d("Final image received! " +
                //                                "Size %d x %d",
                //                        "Quality level %d, good enough: %s, full quality: %s",
                //                        imageInfo.getWidth(),
                //                        imageInfo.getHeight(),
                //                        qualityInfo.getQuality(),
                //                        qualityInfo.isOfGoodEnoughQuality(),
                //                        qualityInfo.isOfFullQuality());
            }

            override fun onIntermediateImageSet(id: String?, imageInfo: ImageInfo?) {
                FLog.d(javaClass, "Intermediate image received")
            }

            override fun onFailure(id: String?, throwable: Throwable?) {
                FLog.e(javaClass, throwable, "Error loading %s", id)
            }
        }

        val uri = Uri.parse(if (TextUtils.isEmpty(url)) "" else url)
        val imageRequest = ImageRequestBuilder.newBuilderWithSource(uri).build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setOldController(simpleDraweeView.controller)
                .setImageRequest(imageRequest)
                // other setters
                .build()
        simpleDraweeView.controller = controller
    }
}
