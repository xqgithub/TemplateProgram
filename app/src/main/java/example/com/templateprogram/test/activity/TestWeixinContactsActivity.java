package example.com.templateprogram.test.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.adapter.CityAdapter;
import example.com.templateprogram.test.bean.CountryCode;
import example.com.templateprogram.utils.KeyboardUtils;
import example.com.templateprogram.utils.LocalJsonResolutionUtils;

/**
 * Created by beijixiong on 2018/10/21.
 * 介绍：高仿微信通讯录界面
 * 头部不是HeaderView 因为头部也需要快速导航，"↑"
 */

public class TestWeixinContactsActivity extends BaseActivity {
    private static final String TAG = "zxt";
    private static final String INDEX_STRING_TOP = "↑";
    private RecyclerView mRv;
    private CityAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CountryCode.DataBean> mDatas = new ArrayList<>();

    private SuspensionDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;
    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;
    //搜索输入框
    private EditText et_weixin_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_weixincontacts;
    }

    /**
     * 初始化控件
     */
    public void initView() {
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        //使用indexBar
        mTvSideBarHint = findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = findViewById(R.id.indexBar);//IndexBar
        et_weixin_content = findViewById(R.id.et_weixin_content);//IndexBar

        initData();
        initListener();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        mAdapter = new CityAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
//        mRv.addItemDecoration(new DividerItemDecoration(TestWeixinContactsActivity.this, DividerItemDecoration.VERTICAL_LIST));
        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager

        //模拟线上加载数据
        initDatas2();
    }

    /**
     * 初始化监听
     */
    @SuppressLint("ClickableViewAccessibility")
    public void initListener() {
        //监听EditText内容变化
        et_weixin_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = s.length();
                searchData(s);
            }
        });
        //监听EditText是否获取到焦点
        et_weixin_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                } else {
                }
            }
        });
        //禁止EditText输入空格
        setEditTextInhibitInputSpace(et_weixin_content);

        //侧滑栏点击监听
        mIndexBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mIndexBar.setFocusable(true);
                mIndexBar.setFocusableInTouchMode(true);
                mIndexBar.requestFocus();
                KeyboardUtils.hideSoftInput(TestWeixinContactsActivity.this);
                return false;
            }
        });

    }

    private void initDatas2() {
        //得到本地json文本内容
        String fileName = "country.json";
        String json = LocalJsonResolutionUtils.getJson(TestWeixinContactsActivity.this, fileName);
        //转换为对象
        CountryCode countryCode = LocalJsonResolutionUtils.JsonToObject(json, CountryCode.class);
        mDatas = countryCode.getData();
        mAdapter.setDatas(mDatas);
        mAdapter.notifyDataSetChanged();
        mIndexBar.setmSourceDatas(mDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(mDatas);
    }


    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 搜索数据
     */
    public void searchData(Editable content) {
        mDatas.clear();
        //得到本地json文本内容
        String fileName = "country.json";
        String json = LocalJsonResolutionUtils.getJson(TestWeixinContactsActivity.this, fileName);
        //转换为对象
        CountryCode countryCode = LocalJsonResolutionUtils.JsonToObject(json, CountryCode.class);
        if (content.length() > 0) {
            for (CountryCode.DataBean databean : countryCode.getData()) {
                if ((databean.getZh() + databean.getCode()).contains(content)) {
                    mDatas.add(databean);
                }
            }
        } else {
            for (CountryCode.DataBean databean : countryCode.getData()) {
                mDatas.add(databean);
            }
        }
        mIndexBar.setmSourceDatas(mDatas)
                .invalidate();
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {
//        //延迟两秒 模拟加载数据中....
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mDatas = new ArrayList<>();
//                //微信的头部 也是可以右侧IndexBar导航索引的，
//                // 但是它不需要被ItemDecoration设一个标题titile
//                mDatas.add((CityBean) new CityBean("新的朋友").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
//                mDatas.add((CityBean) new CityBean("群聊").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
//                mDatas.add((CityBean) new CityBean("标签").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
//                mDatas.add((CityBean) new CityBean("公众号").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
//                for (int i = 0; i < data.length; i++) {
//                    CityBean cityBean = new CityBean();
//                    cityBean.setCity(data[i]);//设置城市名称
//                    mDatas.add(cityBean);
//                }
//                mAdapter.setDatas(mDatas);
//                mAdapter.notifyDataSetChanged();
//
//                mIndexBar.setmSourceDatas(mDatas)//设置数据
//                        .invalidate();
//                mDecoration.setmDatas(mDatas);
//            }
//        }, 500);
    }

    /**
     * 更新数据源
     *
     * @param view
     */
    public void updateDatas(View view) {
//        for (int i = 0; i < 5; i++) {
//            mDatas.add(new CityBean("东京"));
//            mDatas.add(new CityBean("大阪"));
//        }
//        mIndexBar.setmSourceDatas(mDatas)
//                .invalidate();
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDatas != null) {
            mDatas.clear();
            mDatas = null;
        }
    }
}
