package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.view.CustomVideoView;

/**
 * Created by XQ on 2018/6/9.
 * 视频广告播放
 */

public class TestVideoViewActivity extends BaseActivity {


    private Activity mActivity;
    private CustomVideoView cv_videoview;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        /**播放视频**/
        cv_videoview.playVideo(uri);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_videoview;
    }

    /**
     * 加载UI
     */
    public void initView() {
        mActivity = TestVideoViewActivity.this;
        cv_videoview = (CustomVideoView) findViewById(R.id.cv_videoview);
        uri = uri = Uri.parse("android.resource://" + mActivity.getPackageName() + "/" + R.raw.test1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cv_videoview != null) {
            cv_videoview.stopPlayback();
        }
    }
}
