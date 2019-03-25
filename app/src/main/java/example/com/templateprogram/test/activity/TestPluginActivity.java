package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

/**
 * Created by beijixiong on 2019/3/12.
 * 测试插件功能
 */

public class TestPluginActivity extends BaseActivity {


    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.btn2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_plugin;
    }

    public void initData() {
    }


    @OnClick({R.id.btn, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                break;
            case R.id.btn2:
                break;
        }
    }
}
