package com.core.glide;

import android.content.Context;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Glide 相关工具和数据
 *
 * @author a_liYa
 * @date 2018/1/9 15:50.
 */
public class GlideMode {

    public static final int SAVE_DEFAULT = 0x00;
    public static final int SAVE_MOBILE = 0x01;
    public static final int SAVE_WIFI = 0x02;
    public static final int SAVE_MOBILE_WIFI = 0x03;

    private static Context sContext;
    private static int sSaveFlow = SAVE_DEFAULT;
    private static boolean sWebpEnable = true;

    public static void setContext(Context context) {
        if (context != null && sContext == null) {
            sContext = context.getApplicationContext();
        }
    }

    public static Context getContext() {
        return sContext;
    }

    public static boolean isWebpEnable() {
        return sWebpEnable;
    }

    public static void setWebpEnable(boolean webpEnable) {
        GlideMode.sWebpEnable = webpEnable;
    }

    public static void setSaveFlow(@SaveFlow int saveFlow) {
        GlideMode.sSaveFlow = saveFlow;
    }

    /**
     * 获取当前省流量模式
     *
     * @return {@link SaveFlow}
     */
    public static @SaveFlow int getSaveFlow() {
        return sSaveFlow;
    }

    /**
     * 当前模式是否包含指定模式 saveFlow
     *
     * @param saveFlow 指定模式
     * @return true:包含
     */
    public static boolean containsSaveFlow(@SaveFlow int saveFlow) {
        if (saveFlow == SAVE_DEFAULT && GlideMode.sSaveFlow != SAVE_DEFAULT) {
            return false;
        }
        return (GlideMode.sSaveFlow & saveFlow) == saveFlow;
    }

    /**
     * 设置移动网络下是否开启节省流量
     *
     * @param isProvincialTraffic true:开启
     * @see #setSaveFlow(int)
     * @deprecated 过时
     */
    @Deprecated
    public static void setProvincialTraffic(boolean isProvincialTraffic) {
        GlideMode.sSaveFlow = isProvincialTraffic ? SAVE_MOBILE : SAVE_DEFAULT;
    }

    /**
     * 移动网络下是否已开启节省流量
     *
     * @return true:已开启
     * @see #getSaveFlow()
     * @deprecated 过时
     */
    @Deprecated
    public static boolean isProvincialTraffic() {
        return (GlideMode.sSaveFlow & SAVE_MOBILE) == SAVE_MOBILE;
    }

    @IntDef({SAVE_DEFAULT, SAVE_MOBILE, SAVE_WIFI, SAVE_MOBILE_WIFI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SaveFlow {
    }

}
