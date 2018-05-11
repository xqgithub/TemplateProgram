package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.adapter.GirdRecyclerAdapter;

/**
 * Created by admin on 2018/5/10.
 */

public class TestGirdRecyclerViewActivity extends BaseActivity {


    private Activity mActivity;
    // RecyclerView的申明
    private RecyclerView rv_test_girdrecycler;
    private GridLayoutManager layoutManager;
    private GirdRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initTestData();
        setRecyclerView();
    }

    /**
     * 加载UI
     */
    public void initView() {
        mActivity = this;
        rv_test_girdrecycler = (RecyclerView) findViewById(R.id.rv_test_girdrecycler);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_girdrecycler;
    }


    private void setRecyclerView() {
        layoutManager = new GridLayoutManager(this, 6, GridLayoutManager.VERTICAL, false);
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (position == 1) {
//                    return 2;
//                }
//                return 1;
//            }
//        });
        rv_test_girdrecycler.setLayoutManager(layoutManager);
        adapter = new GirdRecyclerAdapter(mActivity, mDatas);
        rv_test_girdrecycler.setAdapter(adapter);
    }


    /**
     * 测试数据
     */
    private List<String> mDatas;
    private String[] tv1 = {"准时",
            "非常绅士",
            "非常有礼貌",
            "很会照顾女生",
            "我的男神是个大暖男哦",
            "谈吐优雅",
            "送我到楼下",
            "迟到",
            "态度恶劣",
            "有不礼貌行为",
            "有侮辱性语言有暴力倾向",
            "人身攻击",
            "临时改变行程",
            "客户迟到并无理要求延长约会时间"};


    public void initTestData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < tv1.length; i++) {
            mDatas.add(tv1[i]);
        }
    }
}
