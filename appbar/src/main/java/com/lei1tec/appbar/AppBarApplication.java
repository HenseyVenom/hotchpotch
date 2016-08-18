package com.lei1tec.appbar;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;

/**
 * Created by li wen hao on 2016/8/17.
 *
 * @since 0.0.1
 */
public class AppBarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
}
