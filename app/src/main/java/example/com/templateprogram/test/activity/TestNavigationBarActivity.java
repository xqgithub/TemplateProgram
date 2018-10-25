package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.view.navigationbar.ChildFragment;
import example.com.templateprogram.test.view.navigationbar.NavitationFollowScrollLayout;
import example.com.templateprogram.test.view.navigationbar.NavitationLayout;
import example.com.templateprogram.test.view.navigationbar.TabNavitationLayout;
import example.com.templateprogram.test.view.navigationbar.ViewPagerAdapter;

/**
 * Created by beijixiong on 2018/10/25.
 * 滑动导航栏
 */

public class TestNavigationBarActivity extends BaseActivity {
    private NavitationFollowScrollLayout navitationFollowScrollLayout;
    private ViewPager viewPager;
    private String[] titles = new String[]{"标题一", "标题二", "标题三", "标题四", "标题五", "标题六", "标题七", "标题八", "标题九", "标题十"};
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments;


    private NavitationLayout navitationLayout;
    private ViewPager viewPager1;
    private String[] titles1 = new String[]{"标题一", "标题二", "标题三","标题四"};
    private ViewPagerAdapter viewPagerAdapter1;
    private List<Fragment> fragments1;


    private TabNavitationLayout tabNavitationLayout;
    private String[] titles2 = new String[]{"标题一", "标题二", "标题三"};
    private ViewPagerAdapter viewPagerAdapter2;
    private List<Fragment> fragments2;
    private ViewPager viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = findViewById(R.id.viewpager);
        navitationFollowScrollLayout = findViewById(R.id.bar);
        fragments = new ArrayList<>();
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdapter);
        navitationFollowScrollLayout.setViewPager(this, titles, viewPager,
                R.color.feedback_bg_pre, R.color.full_red,
                16, 16, 12, true,
                R.color.button_clickable, 0f, 15f, 15f, 100);
        navitationFollowScrollLayout.setBgLine(this, 0, R.color.colorAccent);
        navitationFollowScrollLayout.setNavLine(this, 3, R.color.appblack);

        navitationFollowScrollLayout.setOnTitleClickListener(new NavitationFollowScrollLayout.OnTitleClickListener() {
            @Override
            public void onTitleClick(View v) {
                Toast.makeText(TestNavigationBarActivity.this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        navitationFollowScrollLayout.setOnNaPageChangeListener(new NavitationFollowScrollLayout.OnNaPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //----------------------
        viewPager1 = findViewById(R.id.viewpager1);
        navitationLayout = findViewById(R.id.bar1);
        fragments1 = new ArrayList<>();
        fragments1.add(new ChildFragment());
        fragments1.add(new ChildFragment());
        fragments1.add(new ChildFragment());
        fragments1.add(new ChildFragment());
        viewPagerAdapter1 = new ViewPagerAdapter(getSupportFragmentManager(), fragments1);
        viewPager1.setAdapter(viewPagerAdapter1);
        navitationLayout.setViewPager(this, titles1, viewPager1, R.color.feedback_bg_pre, R.color.full_red, 16, 16, 0, 0, true);
        navitationLayout.setBgLine(this, 1, R.color.colorAccent);
        navitationLayout.setNavLine(this, 3, R.color.colorPrimary, 0);
        //------------------------
        viewPager2 = findViewById(R.id.viewpager2);
        tabNavitationLayout = findViewById(R.id.bar2);
        fragments2 =  new ArrayList<>();
        fragments2.add(new ChildFragment());
        fragments2.add(new ChildFragment());
        fragments2.add(new ChildFragment());
        viewPagerAdapter2 = new ViewPagerAdapter(getSupportFragmentManager(), fragments2);
        viewPager2.setAdapter(viewPagerAdapter2);
        tabNavitationLayout.setViewPager(this, titles2, viewPager2,
                R.drawable.drawable_left, R.drawable.drawable_mid, R.drawable.drawable_right,
                R.color.appwhite, R.color.feedback_bg_pre, 16, 0, 1f, true);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_navigationbar;
    }

    @Override
    protected void onResume() {
        super.onResume();
        navitationFollowScrollLayout.setCurrentItem(3);
    }
}
