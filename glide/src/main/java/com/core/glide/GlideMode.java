package com.core.glide;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Glide 相关工具和数据
 *
 * @author a_liYa
 * @date 2018/1/9 15:50.
 */
public class GlideMode {

    private static Context sContext;
    private static boolean isProvincialTraffic;

    public static void setContext(Context context) {
        if (context != null) {
            sContext = context.getApplicationContext();
        }
    }

    public static void setProvincialTraffic(boolean isProvincialTraffic) {
        GlideMode.isProvincialTraffic = isProvincialTraffic;
    }

    public static boolean isProvincialTraffic() {
        return GlideMode.isProvincialTraffic;
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

}
