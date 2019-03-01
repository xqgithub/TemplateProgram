package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.glide.GlideUtils;


/**
 * Created by admin on 2018/5/5.
 */

public class TestGlideActivity extends BaseActivity implements View.OnClickListener {

    private Activity mActivity;
    private ImageView iv_glide;
    private Button btn_glide;
    private Button btn_cache_address;
    private String picurl = "https://out.llzggzjy.com:8080/test/update/android/100.jpg";
    private String picurl2 = "http://c2.78dm.net/forum/201108/10/210446hvppajqkej1vbqle.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    /**
     * 加载UI
     */
    public void initView() {
        mActivity = this;
        iv_glide = findViewById(R.id.iv_glide);
        btn_glide = findViewById(R.id.btn_glide);
        btn_cache_address = findViewById(R.id.btn_cache_address);


        btn_glide.setOnClickListener(this);
        btn_cache_address.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_glide:
                Glide.with(mActivity)
                        .load(picurl)
//                        .thumbnail(0.2f)//缩略图
//                        .dontAnimate()//关闭动画
//                        .crossFade(1000)//动画的持续时间，单位ms
//                        .override(10, 10)//图片裁剪,这里的单位是px
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(iv_glide);

                break;
            case R.id.btn_cache_address:
                File cacheFile = GlideUtils.getInstance().getCacheFile(picurl);
                try {
                    LogUtils.i("缓存地址 =-=" + cacheFile.getAbsolutePath());
                } catch (Exception e) {
                    LogUtils.e(e.getMessage());
                }
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_glide;
    }


    /**
     * 加载缩略图
     */
//    private void loadImageThumbnailRequest() {
//        // setup Glide request without the into() method
//        DrawableRequestBuilder<String> thumbnailRequest = Glide.with(mActivity).load(picurl2);
//        // pass the request as a a parameter to the thumbnail request
//        Glide.with(mActivity)
//                .load(picurl2)
//                .thumbnail(thumbnailRequest)
//                .into(iv_glide);
//    }


}
