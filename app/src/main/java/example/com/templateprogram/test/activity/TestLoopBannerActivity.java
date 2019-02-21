package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.donkingliang.banner.CustomBanner;

import java.util.ArrayList;
import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.PicassoUtils;
import example.com.templateprogram.utils.ToastUtils;

/**
 * Created by beijixiong on 2019/2/20.
 * 轮播图
 */

public class TestLoopBannerActivity extends BaseActivity {


    private Activity mActivity;

    private CustomBanner banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = TestLoopBannerActivity.this;
        //加载网络图片
        PicassoUtils.initPicasso(mActivity);

        banner = findViewById(R.id.banner);
        List<String> imageurl_list = new ArrayList<>();
        imageurl_list.add("https://out.llzggzjy.com:8080/test/update/android/lunbo1.jpg");
        imageurl_list.add("https://out.llzggzjy.com:8080/test/update/android/lunbo2.jpg");
        imageurl_list.add("https://out.llzggzjy.com:8080/test/update/android/lunbo3.jpg");
        setOrdinary(imageurl_list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_loopbanner;
    }

    /**
     * 设置普通适配器
     */
    private void setOrdinary(List<String> imageurl_list) {
        banner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, final int position, String entity) {
                PicassoUtils.loadBannerFromUrl(entity, (ImageView) view);
            }
        }, imageurl_list)
                .setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY)//设置指示器类型，有普通指示器(ORDINARY)、数字指示器(NUMBER)和没有指示器(NONE)三种类型。
                .setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect)//设置两个点图片作为翻页指示器，只有指示器为普通指示器(ORDINARY)时有用
                .setIndicatorInterval(20)//设置指示器的指示点间隔，只有指示器为普通指示器(ORDINARY)时有用。
                .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER)//设置指示器的方向。
                .startTurning(5000);//设置轮播图自动滚动轮播，参数是轮播图滚动的间隔时间
        banner.setOnPageClickListener(new CustomBanner.OnPageClickListener() {
            @Override
            public void onPageClick(int position, Object o) {
                ToastUtils.showLongToastSafe("position =-= " + position);
            }
        });
    }


}
