package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.ToastUtils;

/**
 * Created by admin on 2018/5/5.
 */

public class TestGlideActivity extends BaseActivity implements View.OnClickListener {

    private Activity mActivity;
    private ImageView iv_glide;
    private Button btn_glide;
    private String picurl = "http://img2.ph.126.net/eNn_cPC_5ilZcnbUfWb5yA==/1637339939626057409.jpg";
    private String picurl2 = "http://c2.78dm.net/forum/201108/10/210446hvppajqkej1vbqle.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        if (clearCacheDiskSelf()) {
            ToastUtils.showLongToast("清除缓存成功");
        } else {
            ToastUtils.showLongToast("清除缓存失败");
        }
    }


    /**
     * 加载UI
     */
    public void initView() {
        mActivity = this;
        iv_glide = (ImageView) findViewById(R.id.iv_glide);
        btn_glide = (Button) findViewById(R.id.btn_glide);


        btn_glide.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_glide:
                Glide.with(mActivity)
                        .load(picurl2)
//                        .thumbnail(0.2f)//缩略图
//                        .dontAnimate()//关闭动画
//                        .crossFade(1000)//动画的持续时间，单位ms
                        .override(10, 10)//图片裁剪,这里的单位是px
                        .into(iv_glide);

                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_glide;
    }


    /**
     * 清除Glide的缓存
     *
     * @return
     */
    public boolean clearCacheDiskSelf() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(mActivity).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(mActivity).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 加载缩略图
     */
    private void loadImageThumbnailRequest() {
        // setup Glide request without the into() method
        DrawableRequestBuilder<String> thumbnailRequest = Glide.with(mActivity).load(picurl2);
        // pass the request as a a parameter to the thumbnail request
        Glide.with(mActivity)
                .load(picurl2)
                .thumbnail(thumbnailRequest)
                .into(iv_glide);
    }

}
