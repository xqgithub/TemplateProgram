package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.view.Rotate3dAnimation;
import example.com.templateprogram.test.view.RotateAnimation;
import example.com.templateprogram.utils.LogUtils;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = TestAnimationActivity.this;
        iv_animation = findViewById(R.id.iv_animation);
        rl_animation = findViewById(R.id.rl_animation);
        iv_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Flip2D();
                Flip3DOpen();
//                Flip3DImitate();
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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_animation;
    }
}
