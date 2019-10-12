package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.LogUtils;

/**
 * Created by Administrator on 2019/10/10.
 */

public class TestBottomLayoutThreeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("onCreate =-= ");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_bottomlayoutthree;
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.i("onStart =-= ");
    }


    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i("onResume =-= ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i("onDestroy =-= ");
    }
}
