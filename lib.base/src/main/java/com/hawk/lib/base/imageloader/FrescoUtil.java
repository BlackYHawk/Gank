package com.hawk.lib.base.imageloader;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created By Hawk on 2018/8/25.
 */
public class FrescoUtil {
    private static final int INVALID = -1;

    public static void load(SimpleDraweeView draweeView, Uri uri) {
        load(draweeView, uri, INVALID, INVALID);
    }

    public static void load(SimpleDraweeView draweeView, Uri uri, int width, int height){
        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(uri)
                        // 开启Downsampling:在初始化时设置.setDownsampleEnabled(true)
                        .setProgressiveRenderingEnabled(true)//支持图片渐进式加载
                        .setRotationOptions(RotationOptions.autoRotate()); //如果图片是侧着,可以自动旋转

        if (width != INVALID && height != INVALID) {
            //缩放,在解码前修改内存中的图片大小, 配合Downsampling可以处理所有图片,否则只能处理jpg
            requestBuilder.setResizeOptions(new ResizeOptions(width, height));
        }

        ImageRequest request =  requestBuilder.build();

        PipelineDraweeController controller =
                (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(draweeView.getController())
                        .setAutoPlayAnimations(false) //自动播放gif动画
                        .build();

        draweeView.setController(controller);
    }

}
