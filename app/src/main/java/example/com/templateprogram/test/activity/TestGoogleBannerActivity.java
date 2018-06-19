package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.base.MyApp;
import example.com.templateprogram.utils.DeviceUtils;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.StringUtils;

/**
 * Created by XQ on 2018/5/21.
 * 横幅广告
 */

public class TestGoogleBannerActivity extends BaseActivity implements View.OnClickListener {


    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化移动广告SDK。
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        // 获取在layout / ad_fragment.xml中定义的广告视图，广告单元ID设置为values/strings.xml.
        adView = (AdView) findViewById(R.id.ad_view);
        //创建一个广告请求,检查你的logcat输出的散列设备ID在物理设备上获取测试广告
        //例如：使用AdRequest.Builder.addTestDevice（”ABCDEF012345“）在该设备上获取测试广告
        String androidid = Settings.Secure.getString(MyApp.getApplication().getContentResolver(), Settings.Secure.ANDROID_ID)
                .toLowerCase();//统一使用小写，是否存在不同API获取的结果大小写不同的情况未知
        if (StringUtils.isBlank(androidid)) {
            androidid = "";
        } else {
            if ("9774d56d682e549c".equals(androidid)) {
                androidid = "";
            }
        }
//        LogUtils.i("deviceid----->" + DeviceUtils.getMd5(androidid).toUpperCase());
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(DeviceUtils.getMd5(androidid).toUpperCase()).build();
//        AdRequest adRequest = new AdRequest.Builder().build();
        // 开始在后台加载广告。
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //代码在用户即将返回时执行
                // 在点击广告后向应用发送信息。
                LogUtils.i("----->onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                //代码在广告请求失败时执行。
                LogUtils.i("----->onAdFailedToLoad");
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                //用户离开应用程序时要执行的代码。
                LogUtils.i("----->onAdLeftApplication");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                //代码在广告打开覆盖层时执行
                //覆盖屏幕。
                LogUtils.i("----->onAdOpened");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                //代码在广告加载完成时执行。
                LogUtils.i("----->onAdLoaded");
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                LogUtils.i("----->onAdClicked");
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                LogUtils.i("----->onAdImpression");
            }
        });


//        LogUtils.i("isTestDevice----->" + adRequest.isTestDevice(TestGoogleBannerActivity.this));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_googlebanner;
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
