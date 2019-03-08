package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

    //刷新控件
    private RefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    /**
     * 加载UI
     */
    public void initView() {
        rv_test_main = (RecyclerView) findViewById(R.id.rv_test_main);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        layoutManager = new LinearLayoutManager(this);// 布局管理器
//        SnapHelper snapHelper = new LinearSnapHelper();//可以很方便的实现类似 ViewPager 的效果，比ViewPager 效果更好
//        snapHelper.attachToRecyclerView(rv_test_main);

        initData();
        initListener();
    }

    /**
     * 加载监听
     */
    // 最后一个完全可见项的位置
    private int findLastVisibleItemPosition;

    public void initListener() {
        //下拉刷新监听
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshdata();
                recyclerAdapter.setData(mDatas);
                refreshLayout.finishRefresh(true);
                refreshLayout.setNoMoreData(false);
                recyclerAdapter.notifyDataSetChanged();
            }
        });
        //上滑加载更多监听
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }
        });

        rv_test_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    findLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                }
//                LogUtils.i("findLastVisibleItemPosition =-= " + findLastVisibleItemPosition);
            }
        });
    }

    /**
     * 初始化数据
     */
    public void initData() {
        initTestData();
        recyclerAdapter = new MainRecyclerAdapter(TestMainActivity.this, mDatas);
        rv_test_main.setHasFixedSize(true);
        rv_test_main.setLayoutManager(layoutManager);// 设置布局管理器
        layoutManager.setOrientation(OrientationHelper.VERTICAL);// 设置为垂直布局，这也是默认的
        rv_test_main.addItemDecoration(new DividerItemDecoration1(this,
                OrientationHelper.VERTICAL));// 设置分割线
//        rv_test_main.addItemDecoration(new SpaceItemDecoration(100));
        rv_test_main.setItemAnimator(new DefaultItemAnimator());// 设置增加或删除条目的动画
        rv_test_main.setAdapter(recyclerAdapter);// 设置适配器

        //设置 Header
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        //设置 Footer
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
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
            "TestFlipActivity", "TestAESCryptActivity", "TestBrokenGlassActivity",
            "TestRippleAnimationActivity", "TestViewProliferationActivity", "TestAnimationActivity",
            "TestGreenDaoActivity", "TestEventBusActivityOne", "TestBitmapCompress",
            "TestImageQRCodeActivity", "TestPingActivity", "TestArouseAppActivity",
            "TestAPIEncryptActivity", "TestLoopBannerActivity", "TestSPEncryptDecryptActivity",
            "TestMarqueeTextViewActivity"
    };


    public void initTestData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < tv1.length; i++) {
            mDatas.add(tv1[i]);
        }
    }

    private int tempnum = 1;

    public void refreshdata() {
        mDatas.clear();
        for (int i = 0; i < tv1.length; i++) {
            mDatas.add(tv1[i] + tempnum);
        }
        tempnum++;
    }


}
