package example.com.templateprogram.test.activity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.OnClick;
import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.StringUtils;

/**
 * Created by Administrator on 2019/10/10.
 */

public class TestBottomLayoutOneActivity extends BaseActivity {

    protected LocalActivityManager mLocalActivityManager;
    private static final String STATES_KEY = "android:states";
    @BindView(R.id.frame)
    FrameLayout mBoday;


    private String tag_twoactivity = "tag_twoactivity";
    private String tag_threeactivitytag = "tag_threeactivitytag";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEmbeddedActivity(savedInstanceState, tag_twoactivity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_bottomlayoutone;
    }


    @OnClick({R.id.btn_A, R.id.btn_B, R.id.btn_C})
    public void onViewClicked(View view) {
        String CurrentId = mLocalActivityManager.getCurrentId();
        LogUtils.i("CurrentId =-= " + CurrentId);
        switch (view.getId()) {
            case R.id.btn_A:
                switchEmbeddedActivity(CurrentId, tag_twoactivity, TestBottomLayoutTwoActivity.class);
                break;
            case R.id.btn_B:
                switchEmbeddedActivity(CurrentId, tag_threeactivitytag, TestBottomLayoutThreeActivity.class);
                break;
            case R.id.btn_C:
                break;
        }
    }


    private void initEmbeddedActivity(Bundle savedInstanceState, String tagActivity) {
        mLocalActivityManager = new LocalActivityManager(this, true);
//        Bundle states = savedInstanceState != null ? (Bundle) savedInstanceState.getBundle(STATES_KEY) : null;
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        mLocalActivityManager.dispatchResume();


        Intent intent = new Intent(TestBottomLayoutOneActivity.this, TestBottomLayoutTwoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        View v = mLocalActivityManager.startActivity(tagActivity, intent).getDecorView();
        mBoday.addView(v);
    }

    private void switchEmbeddedActivity(String CurrentId, String tagActivity, Class<?> cls) {
        if (!StringUtils.isBlank(CurrentId) && !CurrentId.equals(tagActivity)) {
            mBoday.removeView(mLocalActivityManager.destroyActivity(CurrentId, true).getDecorView());
            Intent intent = new Intent(TestBottomLayoutOneActivity.this, cls);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            View v = mLocalActivityManager.startActivity(tagActivity, intent).getDecorView();
            mBoday.addView(v);
        }
    }
}
