package example.com.templateprogram.test.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.ryan.rv_gallery.AnimManager;
import com.ryan.rv_gallery.GalleryRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.adapter.RecyclerAdapter;

/**
 * Created by XQ on 2018/8/20.
 * 测试Recyclerview滑动放大缩小效果
 */

public class TestRecyclerviewGalleryActivity extends BaseActivity implements GalleryRecyclerView.OnItemClickListener, RecyclerAdapter.OnItemPhotoChangedListener {

    private GalleryRecyclerView mRecyclerView;
    private RelativeLayout mContainer;
    private SeekBar mSeekbar;

    private Map<String, Drawable> mTSDraCacheMap = new HashMap<>();
    private static final String KEY_PRE_DRAW = "key_pre_draw";

    /**
     * 获取虚化背景的位置
     */
    private int mLastDraPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView = findViewById(R.id.rv_list);
        mContainer = findViewById(R.id.rl_container);
        mSeekbar = findViewById(R.id.seekBar);

        final RecyclerAdapter adapter = new RecyclerAdapter(TestRecyclerviewGalleryActivity.this, getDatas());
        adapter.setOnItemPhotoChangedListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView
                // 设置滑动速度（像素/s）
                .initFlingSpeed(5000)
                // 设置页边距和左右图片的可见宽度，单位dp
                .initPageParams(0, 40)
                // 设置切换动画的参数因子
                .setAnimFactor(0.1f)
                // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
                .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                // 设置点击事件
                .setOnItemClickListener(this)
                // 设置自动播放
                .autoPlay(false)
                // 设置自动播放间隔时间 ms
                .intervalTime(2000)
                // 设置初始化的位置
                .initPosition(1)
                // 在设置完成之后，必须调用setUp()方法
                .setUp();


        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRecyclerView.smoothScrollToPosition(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_recyclerviewgallery;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getApplicationContext(), "position=" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemPhotoChanged() {

    }


    /***
     * 测试数据
     * @return List<Integer>
     */
    public List<Integer> getDatas() {
        TypedArray ar = getResources().obtainTypedArray(R.array.test_arr);
        final int[] resIds = new int[ar.length()];
        for (int i = 0; i < ar.length(); i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        List<Integer> tDatas = new ArrayList<>();
        for (int resId : resIds) {
            tDatas.add(resId);
        }
        return tDatas;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecyclerView != null) {
            // 释放资源
            mRecyclerView.release();
        }
    }
}
