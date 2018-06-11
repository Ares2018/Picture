### Glide二次封装

#### 使用方式

```java
    GlideApp.with();
```

#### 封装功能：

1. 底层网络库结合OkHttp3
2. 省流量模式

    ```
    GlideMode.setProvincialTraffic(true); // 开启省流量，移动网络下不下载图片。
    ```

3. 图片地址Webp拼接

    url&x-oss-process=image/resize,w_`${WIDTH}`,h_`${HEIGHT}`,m_fill/format,webp
   