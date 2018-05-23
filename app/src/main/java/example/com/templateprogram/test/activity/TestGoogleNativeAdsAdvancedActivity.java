package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.List;
import java.util.Locale;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

/**
 * Created by XQ on 2018/5/22.
 * 原生广告高级
 */

public class TestGoogleNativeAdsAdvancedActivity extends BaseActivity {

    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private static final String ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";

    private Button refresh;
    private CheckBox startVideoAdsMuted;
    private TextView videoStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, ADMOB_APP_ID);

        refresh = (Button) findViewById(R.id.btn_refresh);
        startVideoAdsMuted = (CheckBox) findViewById(R.id.cb_start_muted);
        videoStatus = (TextView) findViewById(R.id.tv_video_status);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unusedView) {
                refreshAd();
            }
        });
        refreshAd();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_nativeadsadvanced;
    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        //获取广告的视频控制器。 即使广告没有，也会一直提供
        //拥有视频资源。
        VideoController vc = nativeAd.getVideoController();
        //创建一个新的VideoLifecycleCallbacks对象并将其传递给VideoController。该
        // VideoController将在视频中发生事件时调用此对象上的方法
        // 生命周期。
        vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            public void onVideoEnd() {
                //发布商应该允许原生广告在刷新前完成视频播放
                //或将其替换为同一用户界面位置中的其他广告。
                refresh.setEnabled(true);
                videoStatus.setText("Video status: Video playback has ended.");
                super.onVideoEnd();
            }
        });
        MediaView mediaView = (MediaView) adView.findViewById(R.id.ad_media);
        ImageView mainImageView = (ImageView) adView.findViewById(R.id.ad_image);

        // Apps可以检查VideoController的hasVideoContent属性以确定是否
        // NativeAppInstallAd有一个视频资源。
        if (vc.hasVideoContent()) {
            adView.setMediaView(mediaView);
            mainImageView.setVisibility(View.GONE);
            videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));
        } else {
            adView.setImageView(mainImageView);
            mediaView.setVisibility(View.GONE);

            // At least one image is guaranteed.
            List<NativeAd.Image> images = nativeAd.getImages();
            mainImageView.setImageDrawable(images.get(0).getDrawable());

            refresh.setEnabled(true);
            videoStatus.setText("Video status: Ad does not contain a video asset.");
        }
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // 一些资产保证在每个UnifiedNativeAd中。
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

        //这些资产不保证在每个UnifiedNativeAd中，因此这很重要
        //在尝试显示它们之前检查它们。
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }


    private void refreshAd() {
        refresh.setEnabled(false);
        AdLoader.Builder builder = new AdLoader.Builder(this, ADMOB_AD_UNIT_ID);
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                FrameLayout frameLayout =
                        (FrameLayout) findViewById(R.id.fl_adplaceholder);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                        .inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(startVideoAdsMuted.isChecked())
                .build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                refresh.setEnabled(true);
                Toast.makeText(TestGoogleNativeAdsAdvancedActivity.this, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
        videoStatus.setText("");
    }


}
