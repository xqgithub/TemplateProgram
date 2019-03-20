package example.com.templateprogram.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.utils.StringUtils;


/**
 * Created by beijixiong on 2019/3/5.
 * 跑马灯
 */

public class MarqueeTextView3 extends HorizontalScrollView implements Runnable {

    private Context context;
    private LinearLayout mainLayout;//跑马灯滚动部分
    private int scrollSpeed = 5;//滚动速度
    private int scrollDirection = LEFT_TO_RIGHT;//滚动方向
    private int currentX;//当前x坐标
    private int viewMargin = 20;//View间距
    private int viewWidth;//View总宽度
    private int screenWidth;//屏幕宽度
    private int viewSingleWidth;//单个view的宽度

    public static final int LEFT_TO_RIGHT = 1;
    public static final int RIGHT_TO_LEFT = 2;

    public MarqueeTextView3(Context context) {
        this(context, null);
    }

    public MarqueeTextView3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeTextView3(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    void initView() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        mainLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.scroll_content, null);
        this.addView(mainLayout);
    }

    public void addViewInQueue(List<View> views) {
        if (!StringUtils.isBlank(views) && views.size() > 0) {
            for (int i = 0; i < views.size(); i++) {
                View view = views.get(i);
                if (i == 0) {//第一个view
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 0, 0, 0);
                    view.setLayoutParams(lp);
                    mainLayout.addView(view);
                    view.measure(0, 0);//测量view
                    viewSingleWidth = view.getMeasuredWidth();
                    viewWidth = viewWidth + view.getMeasuredWidth() + viewMargin;
                } else {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(viewMargin, 0, 0, 0);
                    view.setLayoutParams(lp);
                    mainLayout.addView(view);
                    view.measure(0, 0);//测量view
                    viewWidth = viewWidth + view.getMeasuredWidth() + viewMargin;
                }
            }
        }
    }

    //开始滚动
    public void startScroll() {
        removeCallbacks(this);
        currentX = (scrollDirection == LEFT_TO_RIGHT ? viewWidth : 0);
        post(this);
    }

    //停止滚动
    public void stopScroll() {
        removeCallbacks(this);
    }

    //设置View间距
    public void setViewMargin(int viewMargin) {
        this.viewMargin = viewMargin;
    }

    //设置滚动速度
    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    //设置滚动方向 默认从左向右
    public void setScrollDirection(int scrollDirection) {
        this.scrollDirection = scrollDirection;
    }

    /**
     * 得到屏幕的宽度
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * 得到单个view的宽度
     */
    public int getViewSingleWidth() {
        return viewSingleWidth;
    }


    @Override
    public void run() {
        switch (scrollDirection) {
            case LEFT_TO_RIGHT:
                mainLayout.scrollTo(currentX, 0);
                currentX--;

                if (-currentX >= screenWidth) {
                    mainLayout.scrollTo(viewWidth, 0);
                    currentX = viewWidth;
                }
                break;
            case RIGHT_TO_LEFT:
                mainLayout.scrollTo(currentX, 0);
                currentX++;

                if (currentX >= viewWidth) {
                    mainLayout.scrollTo(-screenWidth, 0);
                    currentX = -screenWidth;
                }
                break;
            default:
                break;
        }

        postDelayed(this, 50 / scrollSpeed);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }


}