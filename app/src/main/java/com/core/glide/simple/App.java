package com.core.glide.simple;

import android.app.Application;
import android.util.Log;

/**
 * App
 *
 * @author a_liYa
 * @date 2018/1/19 10:58.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "onCreate: app");
    }

}
