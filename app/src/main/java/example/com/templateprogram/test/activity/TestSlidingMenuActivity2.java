package example.com.templateprogram.test.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.view.SlidingDrawerLayout;

/**
 * Created by beijixiong on 2018/10/24.
 * 上下滑动
 */

public class TestSlidingMenuActivity2 extends BaseActivity implements View.OnClickListener {

    private SlidingDrawerLayout mSlidingDrawer;
    private View mTopBtn, mBottomBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_slidingmenu2;
    }

    public void initView() {
        mSlidingDrawer = findViewById(R.id.slidingDrawer);
        mTopBtn = findViewById(R.id.topBtn);
        mBottomBtn = findViewById(R.id.bottomBtn);

        Resources res = getResources();
        int topBarSize = (int) res.getDimension(R.dimen.deimen_1x);
        int bottomBarSize = (int) res.getDimension(R.dimen.deimen_50x);
        mSlidingDrawer.setTopTabHeight(topBarSize, true);
        mSlidingDrawer.setBottomTabHeight(bottomBarSize, true);

        mTopBtn.setOnClickListener(this);
        mBottomBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.topBtn) {
            if (mSlidingDrawer.isBottomOpened()) {
                mSlidingDrawer.closeBottom();
            } else {
                if (mSlidingDrawer.isTopOpened()) {
                    mSlidingDrawer.closeTop();
                } else {
                    mSlidingDrawer.openTopSync();
                }
            }
        } else if (v.getId() == R.id.bottomBtn) {
            if (mSlidingDrawer.isTopOpened()) {
                mSlidingDrawer.closeTop();
            } else {
                if (mSlidingDrawer.isBottomOpened()) {
                    Resources res = getResources();
                    int bottomBarSize22222 = (int) res.getDimension(R.dimen.deimen_150x);
                    mSlidingDrawer.setBottomTabHeight(bottomBarSize22222, true);
                    mSlidingDrawer.closeBottom();
                } else {
                    mSlidingDrawer.openBottomSync();
                }
            }
        }
    }
}
