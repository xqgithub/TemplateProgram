package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.ToastUtils;

/**
 * Created by beijixiong on 2018/10/23.
 */

public class TestSlidingMenuActivity extends BaseActivity {
    private SlidingMenu menu;

    private TextView tv_left;
    private TextView tv_right;
    private TextView tv_sm_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        tv_sm_left = findViewById(R.id.tv_sm_left);


        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.showMenu();
            }
        });

        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.showSecondaryMenu();
            }
        });

        tv_sm_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLongToast("left");
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_slidingmenu;
    }


    public void initView() {
        menu = new SlidingMenu(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.activity_test_slidingmenu_left);
        //设置右边（二级）侧滑菜单
        menu.setSecondaryMenu(R.layout.activity_test_slidingmenu_right);
        //根据dimension资源文件的ID来设置阴影的宽度
        menu.setShadowWidth(1000);
        //根据资源文件ID来设置滑动菜单的阴影效果
        menu.setShadowDrawable(R.drawable.shadow);
        //设置SlidingMenu离屏幕的偏移量
        menu.setBehindOffset(1);
        //设置SlidingMenu与下方视图的移动的速度比，当为1时同时移动，取值0-1
        menu.setBehindScrollScale(0.1f);
        //设置宽度
        menu.setBehindWidth((int) (getWindowManager().getDefaultDisplay().getWidth() * 0.9));
        // 设置渐入渐出效果的值
        menu.setFadeDegree(1);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置侧滑菜单的位置，可选值LEFT , RIGHT , LEFT_RIGHT （两边都有菜单时设置）
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置二级菜单的阴影效果
//        menu.setSecondaryShadowDrawable(R.drawable.shadow);
        //把滑动菜单添加进所有的Activity中，可选值SLIDING_CONTENT ， SLIDING_WINDOW
//        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
    }
}
