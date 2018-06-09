package example.com.templateprogram.test.activity;

import android.os.Bundle;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

/**
 * Created by xq on 2018/6/9.
 * 加载GIF图片
 */

public class TestGifActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_gif;
    }

    /**
     * 加载UI
     */
    public void initView() {

    }

}
