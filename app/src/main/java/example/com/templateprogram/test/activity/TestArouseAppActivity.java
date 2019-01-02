package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.StringUtils;
import example.com.templateprogram.utils.ToastUtils;

/**
 * Created by beijixiong on 2018/12/27.
 * 通过uri唤起app应用
 */

public class TestArouseAppActivity extends BaseActivity {

    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = TestArouseAppActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_arouse;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                String qq_packagename = "com.tencent.mobileqq";
//                String qq_url = "mqqwpa://im/chat?chat_type=wpa&uin=453453446";
                if (isAppInstalled(mActivity, qq_packagename)) {
                    intentToApp(mActivity, qq_packagename);
                } else {
                    ToastUtils.showLongToastSafe("没有安装QQ，不能唤起！");
                }
                break;
            case R.id.btn2:
                String weixin_packagename = "com.tencent.mm";
                if (isAppInstalled(mActivity, weixin_packagename)) {
                    intentToApp(mActivity, weixin_packagename);
                } else {
                    ToastUtils.showLongToastSafe("没有安装QQ，不能唤起！");
                }
                break;
            case R.id.btn3:
                String weibo_packagename = "com.sina.weibo";
                if (isAppInstalled(mActivity, weibo_packagename)) {
                    intentToApp(mActivity, weibo_packagename);
                } else {
                    ToastUtils.showLongToastSafe("没有安装QQ，不能唤起！");
                }
                break;
        }
    }

    /**
     * 检测第三方App是否已安装
     */
    public boolean isAppInstalled(Context context, String packageName) {
        try {
            if (!StringUtils.isBlank(packageName)) {
                ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, 0);
                if (!StringUtils.isBlank(info)) {
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return false;
    }

    /**
     * 手机中装有potato软件直接唤起，没有的话跳转到下载页面
     * 主要是用于 intent.setAction("android.intent.action.VIEW")方法
     */
    public static void intentToApp(Activity mActivity, String wakeurl) {
        try {
            /**
             * 第一种方式，直接唤起
             */
            Intent intent = mActivity.getPackageManager().getLaunchIntentForPackage(wakeurl);
            mActivity.startActivity(intent);


            /**
             * 第二种方式，唤起
             */
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.VIEW");
//            Uri content_url = Uri.parse(wakeurl);
//            intent.setData(content_url);
//            mActivity.startActivity(intent);


        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }


}
