package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

/**
 * Created by XQ on 2018/5/21.
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
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // 开始在后台加载广告。
        adView.loadAd(adRequest);

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
