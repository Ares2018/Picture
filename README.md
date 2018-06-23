### Glide二次封装

#### 一、添加依赖

```
dependencies {
    compile 'com.core:core-glide:x.x.x'
}
```

#### 二、使用方式

```java
    GlideApp.with();
```

#### 三、新版变化

```
com.zjrb.core.common.glide.GlideApp;
改成
com.core.glide.GlideApp;
```

#### 四、封装功能：

1. 底层网络库结合OkHttp3
2. 省流量模式

    ```
    // 开启省流量，移动网络下不下载图片
    GlideMode.setSaveFlow(GlideMode.SAVE_MOBILE);

    // 开启省流量，WiFi网络下不下载图片
    GlideMode.setSaveFlow(GlideMode.SAVE_WIFI);

    // 开启省流量，移动网络和WiFi网络下都不下载图片
    GlideMode.setSaveFlow(GlideMode.SAVE_MOBILE_WIFI);
    ```

3. 图片地址Webp拼接

    ```
    GlideMode.setWebpEnable(true); // 默认true, 若关闭拼接功能, 请设置false;
    ```
    ```
    拼接规则: 此为浙江新闻规则
    url&x-oss-process=image/resize,w_`${WIDTH}`,h_`${HEIGHT}`,m_fill/format,webp
    ```