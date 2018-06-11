package com.core.glide;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private static int saveFlow = SAVE_DEFAULT;

    public static void setContext(Context context) {
        if (context != null) {
            sContext = context.getApplicationContext();
        }
    }

    public static void setSaveFlow(@SaveFlow int saveFlow) {
        GlideMode.saveFlow = saveFlow;
    }

    public static @SaveFlow int getSaveFlow() {
        return saveFlow;
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
        GlideMode.saveFlow = isProvincialTraffic ? SAVE_MOBILE : SAVE_DEFAULT;
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
        return (GlideMode.saveFlow & SAVE_MOBILE) == SAVE_MOBILE;
    }

    /**
     * 是否为移动网络
     *
     * @return true:移动网络
     */
    public static boolean isMobile() {
        NetworkInfo info = getNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return false;
        }
        return ConnectivityManager.TYPE_MOBILE == info.getType();
    }

    private static NetworkInfo getNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) sContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    @IntDef({SAVE_DEFAULT, SAVE_MOBILE, SAVE_WIFI, SAVE_MOBILE_WIFI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SaveFlow {}

}
