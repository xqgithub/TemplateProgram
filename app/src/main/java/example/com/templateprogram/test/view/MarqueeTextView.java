package example.com.templateprogram.test.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by beijixiong on 2019/3/5.
 * 跑马灯
 */

@SuppressLint("AppCompatCustomView")
public class MarqueeTextView extends TextView {


    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setSingleLine();//使用代码设置单行显示
        setEllipsize(TextUtils.TruncateAt.MARQUEE);//使用代码设置滚动操作
        setFocusableInTouchMode(true);//使用代码设置触摸获取焦点
        setMarqueeRepeatLimit(-1);//设置滚动次数 -1代表无限
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // 重写系统的TextView,让其默认获得焦点
    @Override
    public boolean isFocused() {
        return true;
    }

    //焦点切换调用的方法
    //focused : 焦点是否释放
    //direction : 焦点移动的方向
    //previouslyFocusedRect : 焦点从哪个控件过来
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        //当焦点被抢夺的时候，不能抢夺textview的焦点
        //如果焦点没有被抢夺，调用系统的方法，帮我们保留焦点
        //如果焦点被抢夺了，禁止调用系统的方法，禁止系统移除焦点
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);
        }
    }


}