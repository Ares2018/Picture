package com.core.glide.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.core.glide.GlideMode;
import com.zjrb.core.common.glide.GlideApp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mIvPicture;
    ImageView mIv1;
    ImageView mIv2;
    ImageView mIv3;
    Button mBtnSwitch;

    private String[] urls = new String[]{
            "https://pic4.zhimg.com/02685b7a5f2d8cbf74e1fd1ae61d563b_xll.jpg",
            "https://zjnews.oss-cn-hangzhou.aliyuncs.com/assets/20171018/1508312209694_59e7049103dafc6bbc1d65f9.jpeg",
            "https://stc.zjol.com.cn/g1/M00071ECggSA1k5SpiAJKg_AACimbGXch4600.jpg?width=720&height=481",
            "https://stc.zjol.com.cn/g1/M0006FACggSA1k0G96AShzfAAC8dQMe4-8218.jpg?width=639&height=426",
            "https://stc.zjol.com.cn/g1/M001C78CggSDlkyAVKAUcJQAADXNl82ti4954.jpg?width=720&height=480",
            "https://stc.zjol.com.cn/g1/M0006FACggSA1k0G96AShzfAAC8dQMe4-8218.jpg?width=639&height=426"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (mBtnSwitch = findViewById(R.id.btn_switch)).setOnClickListener(this);
        findViewById(R.id.btn_load).setOnClickListener(this);
        findViewById(R.id.btn_webp).setOnClickListener(this);

        mIv1 = findViewById(R.id.iv_1);
        mIv2 = findViewById(R.id.iv_2);
        mIv3 = findViewById(R.id.iv_3);
        mIvPicture = findViewById(R.id.iv_picture);

        mBtnSwitch.setText(GlideMode.isProvincialTraffic() ? "省流量：开" : "省流量：关");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_switch:
                GlideMode.setProvincialTraffic(!GlideMode.isProvincialTraffic());
                mBtnSwitch.setText(GlideMode.isProvincialTraffic() ? "省流量：开" : "省流量：关");
                break;
            case R.id.btn_load:
                Glide.with(mIvPicture)
                        .asGif()
                        .load("http://img.mp.sohu.com/upload/20170803/3b3d5234f1004fdfa227141283c074e9_th.png")
//                        .apply(AppGlideOptions.smallOptions())
                        .into(mIvPicture);
                break;
            case R.id.btn_webp:
                // 图片 wep拼接加载失败，重试验证逻辑
                GlideApp.with(mIvPicture)
                        .load("https://stcbeta.8531.cn/assets/20171128/1511851486824_5a1d05de03dafc0b3f94627b.gif?w=450&h=800")
//                        .apply(AppGlideOptions.smallOptions())
                        .into(mIvPicture);

                GlideApp.with(mIv1)
                        .load(urls[1])
//                        .apply(AppGlideOptions.smallOptions())
                        .into(mIv1);

                GlideApp.with(mIv2)
                        .load(urls[2])
//                        .apply(AppGlideOptions.smallOptions())
                        .into(mIv2);

                GlideApp.with(mIv3)
                        .load(urls[3])
//                        .apply(AppGlideOptions.smallOptions())
                        .into(mIv3);
                break;
        }

    }
}
