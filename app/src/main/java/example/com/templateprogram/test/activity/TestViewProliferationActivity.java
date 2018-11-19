package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.library.viewspread.helper.BaseViewHelper;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

import static android.view.View.inflate;

/**
 * Created by beijixiong on 2018/11/18.
 * Android 视图扩散切换效果
 */

public class TestViewProliferationActivity extends BaseActivity {
    private BaseViewHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_viewproliferation;
    }

    public void click(View view) {
        if (view.getId() == R.id.btn_translation5) {
            //显示在当前页面跳转
            helper = new BaseViewHelper.Builder(this, view)
//                    .setTranslationView(R.id.iv_second)
                    .setEndView(inflate(this, R.layout.layout_second, null))
                    .isFullWindow(true)
                    .isShowTransition(true)//是否显示过渡动画
//                    .setDimColor(R.color.appblue)//遮罩颜色
                    .setDimAlpha(500)//遮罩透明度
                    .setDuration(500)//过渡时长
                    .setRotation(360)//旋转
                    .create();
            return;
        }
        if (view.getId() == R.id.btn_translation6) {
            View v = inflate(this, R.layout.layout_second, null);
            //显示在当前页面跳转
            helper = new BaseViewHelper.Builder(this, view)
                    .setEndView(v)
                    .create();
            ((TextView) v.findViewById(R.id.tv_message)).setText("我还在第一个页面");
            return;
        }
    }

    @Override
    public void onBackPressed() {
        if (helper != null && helper.isShowing()) {
            helper.back();
        } else {
            super.onBackPressed();
        }
    }
}
