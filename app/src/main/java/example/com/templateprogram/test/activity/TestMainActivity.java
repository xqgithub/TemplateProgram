package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.adapter.MainRecyclerAdapter;
import example.com.templateprogram.test.view.DividerItemDecoration1;

/**
 * Created by XQ on 2017/11/28.
 * 主页显示
 */
public class TestMainActivity extends BaseActivity {

    // RecyclerView的申明
    private RecyclerView rv_test_main;
    private MainRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }


    /**
     * 加载UI
     */
    public void initView() {
        initData();
        rv_test_main = (RecyclerView) findViewById(R.id.rv_test_main);
        rv_test_main.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);// 布局管理器
        rv_test_main.setLayoutManager(layoutManager);// 设置布局管理器
        layoutManager.setOrientation(OrientationHelper.VERTICAL);// 设置为垂直布局，这也是默认的
        rv_test_main.addItemDecoration(new DividerItemDecoration1(this,
                OrientationHelper.VERTICAL));// 设置分割线
//        rv_test_main.addItemDecoration(new SpaceItemDecoration(100));
        rv_test_main.setItemAnimator(new DefaultItemAnimator());// 设置增加或删除条目的动画
        rv_test_main.setAdapter(recyclerAdapter);// 设置适配器


//        SnapHelper snapHelper = new LinearSnapHelper();//可以很方便的实现类似 ViewPager 的效果，比ViewPager 效果更好
//        snapHelper.attachToRecyclerView(rv_test_main);

    }

    /**
     * 加载监听
     */
    public void initListener() {
    }

    /**
     * 初始化数据
     */
    public void initData() {
        initTestData();
        recyclerAdapter = new MainRecyclerAdapter(TestMainActivity.this, mDatas);
    }


    /**
     * 加载布局文件
     *
     * @return 布局文件的ID
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_main;
    }


    /**
     * 测试数据
     */
    private List<String> mDatas;
    private String[] tv1 = {
            "Testone", "TestCopy", "TestMessenger",
            "TestAIDL", "TestJsonResolve", "TestWebViewJS",
            "TestPicasso", "TestDeBugger", "TestFontActivity",
            "TestGlideActivity", "TestGirdRecyclerViewActivity", "TestGoogleBannerActivity",
            "TestGoogleInterstitialActivity", "TestGoogleNativeAdsAdvancedActivity", "TestGoogleRewardedVideoActivity",
            "TestCeilingActivity", "TestVideoViewActivity", "TestGifActivity",
            "TestNotificationActivity", "TestRecyclerviewGalleryActivity", "TestWeixinContactsActivity",
            "TestSlidingMenuActivity", "TestSlidingMenuActivity2", "TestNavigationBarActivity",
            "TestFlipActivity", "TestAESCryptActivity", "TestBrokenGlassActivity"
    };


    public void initTestData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < tv1.length; i++) {
            mDatas.add(tv1[i]);
        }
    }
}
