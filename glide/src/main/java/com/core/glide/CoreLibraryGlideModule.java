package com.core.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;
import com.core.glide.loader.OkHttpUrlLoader;
import com.core.glide.loader.WebpGlideUrlLoader;

import java.io.InputStream;

/**
 * LibraryGlideModule
 *
 * @author a_liYa
 * @date 2017/7/11 17:09.
 */
@GlideModule
public class CoreLibraryGlideModule extends LibraryGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {

        // 网络模块切换成OkHttp
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());

        // 拼接Webp url
        if (GlideMode.isWebpEnable()) {
            registry.prepend(String.class, InputStream.class, new WebpGlideUrlLoader.Factory());
        }

    }
}
