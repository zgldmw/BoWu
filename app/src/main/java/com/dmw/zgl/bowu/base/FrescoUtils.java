package com.dmw.zgl.bowu.base;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Author:          zgl_dmw
 * Email:           2559531803@qq.com
 * Create:          2017/8/14 14:56
 * Update:          2017/8/14 14:56
 * Description:     FrescoUtils
 */

public class FrescoUtils {

    public static void displayImgAspectRatio(final SimpleDraweeView simpleDraweeView, String url) {
        ControllerListener<ImageInfo> controllerListener = new BaseControllerListener<ImageInfo>() {

            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                if (imageInfo == null) {
                    return;
                }
                float aspectRatio = (imageInfo.getWidth() * 1f) / imageInfo.getHeight();
                simpleDraweeView.setAspectRatio(aspectRatio);
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

            @Override
            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                FLog.d(getClass(), "Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                FLog.e(getClass(), throwable, "Error loading %s", id);
            }
        };

        Uri uri = Uri.parse(TextUtils.isEmpty(url) ? "" : url);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri).build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(imageRequest)
                // other setters
                .build();
        simpleDraweeView.setController(controller);
    }
}
