package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fadai.particlesmasher.ParticleSmasher;
import com.fadai.particlesmasher.SmashAnimator;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

/**
 * Created by beijixiong on 2018/11/15.
 * 百叶帘 翻转效果
 */

public class TestBrokenGlassActivity extends BaseActivity implements View.OnClickListener {


    private ParticleSmasher mSmasher;
    private ImageView mIv1, mIv2, mIv3, mIv4, mIv5, mIv6;
    private Button mBtnReset;
    private TextView mTv1;
    private LinearLayout ll_row;
    private LinearLayout ll_row2;

    private long duration = 1000;
    private int startdelay = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSmasher = new ParticleSmasher(this);
        init();
    }

    private void init() {
        mIv1 = (ImageView) findViewById(R.id.iv_main_1);
        mIv2 = (ImageView) findViewById(R.id.iv_main_2);
        mIv3 = (ImageView) findViewById(R.id.iv_main_3);
        mIv4 = (ImageView) findViewById(R.id.iv_main_4);
        mIv5 = (ImageView) findViewById(R.id.iv_main_5);
        mIv6 = (ImageView) findViewById(R.id.iv_main_6);
        mBtnReset = (Button) findViewById(R.id.btn_main_reset);
        mTv1 = (TextView) findViewById(R.id.tv_main_1);
        ll_row = (LinearLayout) findViewById(R.id.ll_row);
        ll_row2 = (LinearLayout) findViewById(R.id.ll_row2);

        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);
        mIv5.setOnClickListener(this);
        mIv6.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);
        mTv1.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btn_main_reset:
//                reset();
                break;
            case R.id.iv_main_1:
                mSmasher.with(ll_row)
                        .setDuration(duration)
                        .setStartDelay(300)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {
                                super.onAnimatorStart();

                            }

                            @Override
                            public void onAnimatorEnd() {
                                super.onAnimatorEnd();
                                reset(R.id.ll_row);
                            }
                        })
                        .start();
                break;
            case R.id.iv_main_2:
                mSmasher.with(ll_row)
                        .setStyle(SmashAnimator.STYLE_DROP)    // 设置动画样式
                        .setDuration(duration)                     // 设置动画时间
                        .setStartDelay(300)                    // 设置动画前延时
                        .setHorizontalMultiple(2)              // 设置横向运动幅度
                        .setVerticalMultiple(2)                // 设置竖向运动幅度
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {
                                super.onAnimatorStart();
                                // 回调，动画开始
                            }

                            @Override
                            public void onAnimatorEnd() {
                                super.onAnimatorEnd();
                                // 回调，动画结束
                                reset(R.id.ll_row);
                            }
                        })
                        .start();
                break;
            case R.id.iv_main_3:
                mSmasher.with(ll_row)
                        .setStyle(SmashAnimator.STYLE_FLOAT_TOP)
                        .setHorizontalMultiple(2)
                        .setVerticalMultiple(3)
                        .setDuration(duration)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {
                                super.onAnimatorStart();
                            }

                            @Override
                            public void onAnimatorEnd() {
                                super.onAnimatorEnd();
                                reset(R.id.ll_row);
                            }
                        })
                        .start();
                break;
            case R.id.iv_main_4:
                mSmasher.with(ll_row2)
                        .setStyle(SmashAnimator.STYLE_FLOAT_BOTTOM)
                        .setHorizontalMultiple(2)
                        .setVerticalMultiple(4)
                        .setDuration(duration)
                        .setStartDelay(500)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {
                                super.onAnimatorStart();
                            }

                            @Override
                            public void onAnimatorEnd() {
                                super.onAnimatorEnd();
                                reset(R.id.ll_row2);
                            }
                        })
                        .start();
                break;
            case R.id.iv_main_5:
                mSmasher.with(ll_row2)
                        .setStyle(SmashAnimator.STYLE_FLOAT_LEFT)
                        .setHorizontalMultiple(2)
                        .setVerticalMultiple(2)
                        .setDuration(duration)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {
                                super.onAnimatorStart();
                            }

                            @Override
                            public void onAnimatorEnd() {
                                super.onAnimatorEnd();
                                reset(R.id.ll_row2);
                            }
                        })
                        .start();
                break;
            case R.id.iv_main_6:
                mSmasher.with(ll_row2)
                        .setStyle(SmashAnimator.STYLE_FLOAT_RIGHT)
                        .setHorizontalMultiple(2)
                        .setVerticalMultiple(2)
                        .setDuration(duration)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {
                                super.onAnimatorStart();
                            }

                            @Override
                            public void onAnimatorEnd() {
                                super.onAnimatorEnd();
                                reset(R.id.ll_row2);
                            }
                        })
                        .start();
                break;

            case R.id.tv_main_1:
                mSmasher.with(view)
                        .setVerticalMultiple(9)
                        .setHorizontalMultiple(3)
                        .setDuration(duration)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorEnd() {
                                super.onAnimatorEnd();
                                mSmasher.reShowView(view);
                                Toast.makeText(TestBrokenGlassActivity.this, "动画结束", Toast.LENGTH_LONG).show();
                            }
                        }).start();
                break;


        }
    }

    private void reset(int viewid) {
        // 让View重新显示
//        mSmasher.reShowView(mIv1);
//        mSmasher.reShowView(mIv2);
//        mSmasher.reShowView(mIv3);
//        mSmasher.reShowView(mIv4);
//        mSmasher.reShowView(mIv5);
//        mSmasher.reShowView(mIv6);
        mSmasher.reShowView(ll_row);
        mSmasher.reShowView(ll_row2);
        reset2(viewid);
    }

    private void reset2(int viewid) {
        switch (viewid) {
            case R.id.ll_row:
                ll_row.setVisibility(View.GONE);
                ll_row2.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_row2:
                ll_row2.setVisibility(View.GONE);
                ll_row.setVisibility(View.VISIBLE);
                break;
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_brokenglass;
    }

}
