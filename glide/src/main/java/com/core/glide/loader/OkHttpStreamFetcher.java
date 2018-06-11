package com.core.glide.loader;

import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.bumptech.glide.util.Synthetic;
import com.core.glide.GlideMode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Fetches an {@link InputStream} using the okhttp library.
 * <p>
 * Code taken from Glide
 * <code>https://github.com/bumptech/glide/tree/master/integration/okhttp3</code>
 *
 * @author a_liYa
 * @date 2017/7/11 16:56.
 */
public class OkHttpStreamFetcher implements DataFetcher<InputStream> {
    private static final String TAG = "OkHttpFetcher";
    private final Call.Factory client;
    private final GlideUrl url;
    @Synthetic
    InputStream stream;
    @Synthetic
    ResponseBody responseBody;
    private volatile Call call;

    public OkHttpStreamFetcher(Call.Factory client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public void loadData(Priority priority, final DataCallback<? super InputStream> callback) {

        if (!GlideMode.containsSaveFlow(GlideMode.SAVE_DEFAULT)) {
            // 图片不响应省流量模式，可通过url传参数 support_spare = false; 默认 true
            if (Uri.parse(url.toStringUrl()).getBooleanQueryParameter("support_spare", true)) {
                if (GlideMode.isMobile() && GlideMode.containsSaveFlow(GlideMode.SAVE_MOBILE)) {
                    callback.onLoadFailed(new IOException("移动网络 - 省流量模式开启"));
                    return;
                }
                if (GlideMode.isWiFi() && GlideMode.containsSaveFlow(GlideMode.SAVE_WIFI)) {
                    callback.onLoadFailed(new IOException("WiFi网络 - 省流量模式开启"));
                    return;
                }
            }
        }

        Request.Builder requestBuilder = new Request.Builder().url(this.url.toStringUrl());
        for (Map.Entry<String, String> headerEntry : this.url.getHeaders().entrySet()) {
            String key = headerEntry.getKey();
            requestBuilder.addHeader(key, headerEntry.getValue());
        }
        Request request = requestBuilder.build();

        call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, "OkHttp failed to obtain result", e);
                }
                callback.onLoadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseBody = response.body();
                if (response.isSuccessful()) {
                    long contentLength = responseBody.contentLength();
                    stream = ContentLengthInputStream.obtain(responseBody.byteStream(),
                            contentLength);
                    callback.onDataReady(stream);
                } else {
                    callback.onLoadFailed(new HttpException(response.message(), response.code()));
                }
            }
        });
    }

    @Override
    public void cleanup() {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            // Ignored
        }
        if (responseBody != null) {
            responseBody.close();
        }
    }

    @Override
    public void cancel() {
        Call local = call;
        if (local != null) {
            local.cancel();
        }
    }

    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
