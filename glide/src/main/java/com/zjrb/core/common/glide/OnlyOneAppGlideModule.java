package com.zjrb.core.common.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.core.glide.GlideMode;

/**
 * AppGlideModule 有且仅能有一个
 * <p>
 * 当前包名为了兼容老代码
 * </p>
 *
 * @author a_liYa
 * @date 2017/7/11 16:16.
 */
@GlideModule
public class OnlyOneAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        GlideMode.setContext(context);
        // apply options.
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context)); // 使用外部存储地址
    }

    /**
     * 为了避免检查元数据（和相关的错误）带来的性能开销
     *
     * @return false
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

}
