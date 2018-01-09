package com.core.glide.loader;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

/**
 * WebpGlideUrlLoader
 *
 * @author a_liYa
 * @date 2017/7/11 19:04.
 */
public class WebpGlideUrlLoader extends BaseGlideUrlLoader<String> {

    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";
    public static final int MODEL_CACHE_SIZE = 10;

    // Glide本身做过缓存，我们的缓存一般情况用不到
    private static ModelCache<String, GlideUrl> sUrlCache = new ModelCache<>(MODEL_CACHE_SIZE);

    protected WebpGlideUrlLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader);
    }

    protected WebpGlideUrlLoader(ModelLoader<GlideUrl, InputStream> concreteLoader,
                                 @Nullable ModelCache<String, GlideUrl> modelCache) {
        super(concreteLoader, modelCache);
    }

    // width、height 有可能等于 View.MeasureSpec.AT_MOST = -2147483648
    @Override
    protected String getUrl(String model, int width, int height, Options options) {
        if (!TextUtils.isEmpty(model)) {
            Uri uri = Uri.parse(model);
            Uri.Builder builder = uri.buildUpon();

            StringBuffer sb = new StringBuffer("image");
            // 1、缩放规则
            {
                sb.append("/resize");
                if (width > 0) {
                    sb.append(",w_").append(width);
                }
                if (height > 0) {
                    sb.append(",h_").append(height);
                }
                // 裁剪策略
                if (width > 0 && height > 0) {
                    sb.append(",m_fill"); // 等同于 android:scaleType="centerCrop"
                } else {
                    sb.append(",m_lfit"); // 等同于 android:scaleType="centerInside"
                }
            }
            // 2、格式转换规则, webp
            {
                String path = uri.getPath();
                if (!TextUtils.isEmpty(path) &&
                        (path.endsWith(".gif") || path.endsWith(".GIF"))) {
                    // gif暂不支持webp转换
                } else {
                    sb.append("/format,webp");
                }
            }
            builder.appendQueryParameter("x-oss-process", sb.toString());
            model = builder.build().toString();
        }
        return model;
    }

    @Override
    public boolean handles(String model) {
        if (!TextUtils.isEmpty(model)) {
            Uri uri = Uri.parse(model);
            String scheme = uri.getScheme();
            if (SCHEME_HTTP.equalsIgnoreCase(scheme) || SCHEME_HTTPS.equalsIgnoreCase(scheme)) {
                return true;
            }
        }
        return false; // 不是网络图片，不拦截
    }

    /**
     * The default factory for {@link WebpGlideUrlLoader}s.
     */
    public static class Factory implements ModelLoaderFactory<String, InputStream> {
        @Override
        public ModelLoader<String, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new WebpGlideUrlLoader(
                    multiFactory.build(GlideUrl.class, InputStream.class), sUrlCache);
        }

        @Override
        public void teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }
}