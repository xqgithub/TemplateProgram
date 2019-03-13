package example.com.templateprogram.test.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.view.Roll3DView;
import example.com.templateprogram.test.view.Rotate3dAnimation;
import example.com.templateprogram.test.view.RotateAnimation;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.ScreenUtils;

/**
 * Created by beijixiong on 2018/11/19.
 * 自定义动画效果
 */

public class TestAnimationActivity extends BaseActivity {

    private Activity mActivity;
    private ImageView iv_animation;
    private RelativeLayout rl_animation;

    //2D翻转实现
    private ScaleAnimation sato0 = new ScaleAnimation(1, 0, 1, 1,
            Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
    private ScaleAnimation sato1 = new ScaleAnimation(0, 1, 1, 1,
            Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);


    //花式3D翻转
    private Roll3DView tdView;
    private Button btn_Pre;
    private Button btn_next;


    private BitmapDrawable bgDrawable1, bgDrawable2;

    //动画先放大，再缩小到正常
    private ImageView iv_animation2;
    private Button btn_zoom;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_animation;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = TestAnimationActivity.this;
        iv_animation = findViewById(R.id.iv_animation);
        iv_animation2 = findViewById(R.id.iv_animation2);
        rl_animation = findViewById(R.id.rl_animation);
        iv_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Flip2D();
                Flip3DOpen();
//                Flip3DImitate();
            }
        });

        tdView = findViewById(R.id.three_d_view);
        btn_Pre = findViewById(R.id.btn_Pre);
        btn_next = findViewById(R.id.btn_next);
        btn_zoom = findViewById(R.id.btn_zoom);

        bgDrawable1 = (BitmapDrawable) getResources().getDrawable(R.mipmap.shouye1);
        bgDrawable2 = (BitmapDrawable) getResources().getDrawable(R.drawable.beauty2);
        Bitmap bitmap1 = bgDrawable1.getBitmap();
        Bitmap bitmap2 = bgDrawable2.getBitmap();
        tdView.addImageBitmap(bitmap1);

        tdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_Pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tdView.toPre();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tdView.toNext();

            }
        });
        tdView.setRollDirection(2);
        tdView.setPartNumber(3);
        tdView.setRollMode(Roll3DView.RollMode.Whole3D);
        tdView.setRotateDegree(90);

        btn_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ZoomOutTOZoomIn();
//                PropertyAnimation();
                ObjectAnimator();
            }
        });
    }

    /**
     * 动画集合
     */
    private void setAnimation() {
        //动画集合实现
        final Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.animation_combo);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LogUtils.i("----->动画开始");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtils.i("----->动画结束");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                LogUtils.i("----->动画重复");
            }
        });
        iv_animation.startAnimation(animation);
    }

    /**
     * 2D翻转
     */
    private void Flip2D() {
        sato0.setDuration(500);
        sato1.setDuration(500);
        iv_animation.startAnimation(sato0);
        sato0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LogUtils.i("----->动画开始");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtils.i("----->动画结束");
                rl_animation.startAnimation(sato1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                LogUtils.i("----->动画重复");
            }
        });
    }

    /**
     * 3D翻转
     */
    private int centerX;
    private int centerY;
    private int depthZ = 100;
    private int duration = 400;
    private Rotate3dAnimation openAnimation;

    private void Flip3DOpen() {
        centerX = rl_animation.getWidth() / 2;
        centerY = rl_animation.getHeight() / 2;
        //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
        openAnimation = new Rotate3dAnimation(0, 90, centerX, centerY, depthZ, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        openAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LogUtils.i("----->动画开始");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtils.i("----->动画结束");
                Flip3Dclose();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.animation_alpha1);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(openAnimation);
//        set.addAnimation(animation);
        rl_animation.startAnimation(set);
    }

    private void Flip3Dclose() {
        //从270到360度，顺时针旋转视图，此时reverse参数为false，达到360度动画结束时视图变得可见
        Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(270, 360, centerX, centerY, depthZ, false);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());

        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.animation_alpha2);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(rotateAnimation);
//        set.addAnimation(animation);
        rl_animation.startAnimation(set);
    }

    /**
     * 3D 翻转 仿ios
     */
    private RotateAnimation rotateAnim;

    private void Flip3DImitate() {
        float cX = rl_animation.getWidth() / 2.0f;
        float cY = rl_animation.getHeight() / 2.0f;
        rotateAnim = new RotateAnimation(cX, cY,
                RotateAnimation.ROTATE_DECREASE);
        rotateAnim.setFillAfter(true);
        rl_animation.startAnimation(rotateAnim);
    }

    /**
     * 补间动画 ，动画先放大再缩小
     */
    private void ZoomOutTOZoomIn() {
        Animation animation_zoomout = AnimationUtils.loadAnimation(mActivity, R.anim.animation_zoomout);
        final Animation animation_zoomin = AnimationUtils.loadAnimation(mActivity, R.anim.animation_zoomin);
        animation_zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_animation2.startAnimation(animation_zoomin);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        iv_animation2.startAnimation(animation_zoomout);
    }

    /**
     * ------------------------ 属性动画 start ------------------------
     */
    private void PropertyAnimation() {//通过不断控制 值 的变化，再不断 手动 赋给对象的属性，从而实现动画效果
        /**
         * ofInt（）作用有两个
         * 1. 创建动画实例
         * 2. 将传入的多个Int参数进行平滑过渡:此处传入0和1,表示将值从0平滑过渡到1
         * 3. 如果传入了3个Int参数 a,b,c ,则是先从a平滑过渡到b,再从b平滑过渡到C，以此类推
         * 4. ValueAnimator.ofInt()内置了整型估值器,直接采用默认的.不需要设置，即默认设置了如何从初始值 过渡到 结束值
         */
        //步骤1：设置动画属性的初始值 & 结束值
        ValueAnimator anim = ValueAnimator.ofInt(ScreenUtils.dip2px(mActivity, 250),
                ScreenUtils.dip2px(mActivity, 300));
        // 步骤2：设置动画的播放各种属性
        // 设置动画运行的时长
        anim.setDuration(500);
        // 设置动画延迟播放时间
        anim.setStartDelay(500);
        // 设置动画重复播放次数 = 重放次数+1
        anim.setRepeatCount(0);
        // 设置重复播放动画模式
        // ValueAnimator.RESTART(默认):正序重放
        // ValueAnimator.REVERSE:倒序回放
        anim.setRepeatMode(ValueAnimator.RESTART);
        // 步骤3：将改变的值手动赋值给对象的属性值：通过动画的更新监听器
        // 设置 值的更新监听器
        // 即：值每次改变、变化一次,该方法就会被调用一次
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (Integer) animation.getAnimatedValue();
                LogUtils.i("currentValue =-= " + currentValue);
                // 步骤4：将改变后的值赋给对象的属性值，下面会详细说明
                iv_animation2.getLayoutParams().width = currentValue;
                iv_animation2.requestLayout();
            }
        });
        anim.start();

        /**
         * oFloat()的用法和 oFloa() 相似
         */

        /**
         * ofObject(),针对的是对象，需要自定义估值器
         */
    }

    /**
     * ValueAnimator 类是先改变值，然后 手动赋值 给对象的属性从而实现动画；是 间接 对对象属性进行操作；
     * ObjectAnimator 类是先改变值，然后 自动赋值 给对象的属性从而实现动画；是 直接 对对象属性进行操作；
     */
    private void ObjectAnimator() {//直接对对象的属性值进行改变操作，从而实现动画效果
        //X轴缩放，先放大，后缩小
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(iv_animation2, "scaleX", 1f, 1.5f, 1f);
        animatorX.setRepeatCount(0);
        animatorX.setRepeatMode(ValueAnimator.RESTART);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(iv_animation2, "scaleY", 1f, 1.5f, 1f);
        animatorY.setRepeatCount(0);
        animatorY.setRepeatMode(ValueAnimator.RESTART);

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorX).with(animatorY);
        animSet.setDuration(1000);
        animSet.start();
    }


    /**
     * ------------------------ 属性动画 end ------------------------
     */


}
