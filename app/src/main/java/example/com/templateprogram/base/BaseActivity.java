package example.com.templateprogram.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.templateprogram.utils.StringUtils;

/**
 * Created by admin on 2017/7/7.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 禁止横竖屏切换
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 去掉系统默认的标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 初始化视图之前操作
        onBeforeSetContentLayout();
        setContentView(getLayoutId());

        // 通过注解绑定控件
        unbinder = ButterKnife.bind(this);
    }

    /**
     * 在设置视图内容之前
     * 需要做什么操作可以写在该方法中
     */
    protected void onBeforeSetContentLayout() {
//        DensityUtils.setDefault(this);
    }

    /**
     * 视图XML id
     * ** 必须要重写 **
     *
     * @return 视图XML id
     */
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!StringUtils.isBlank(unbinder)) {
            unbinder.unbind();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        String name = PublicPracticalMethod.getInstance().getTopActivity(this);
//        if (name.contains("TestMainActivity")) {
//            ToastUtils.showLongToastSafe("这是主界面，不会弹出弹框");
//        } else {
//            PublicPracticalMethod.getInstance().showGlobalDialog(this);
//        }
    }

}
