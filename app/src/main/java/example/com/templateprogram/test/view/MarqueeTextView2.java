package example.com.templateprogram.test.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import example.com.templateprogram.utils.LogUtils;

/**
 * Created by beijixiong on 2019/3/5.
 * 自定义TextView 实现跑马灯效果
 * 启动 暂停 重新启动
 */

@SuppressLint("AppCompatCustomView")
public class MarqueeTextView2 extends TextView {

    /**
     * 第一个滚动
     */
    private Scroller mSlr;
    // 滚动的速度
    private int mRndDuration = 1000;
    //暂停的时候X轴的位移
    private int mXPaused = 0;
    //暂停标识
    private boolean mPaused = true;

    public MarqueeTextView2(Context context) {
        super(context, null);
    }

    public MarqueeTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 这个属性这个View得到焦点,在这里我们设置为true,这个View就永远是有焦点的
     *
     * @return
     */
    @Override
    public boolean isFocused() {
        return true;
    }

    /**
     * 文字开始滚动的位置
     */
    public void startScroll() {
        // 从右边开始
        mXPaused = -1 * getWidth();
        mPaused = true;
        resumeScroll();
    }

    /**
     * 从暂停点恢复滚动
     */
    public void resumeScroll() {
        if (!mPaused) {
            return;
        }
        //不知道为什么它有时不会滚动
        //如果在构造函数中调用了setHorizontallyScrolling。
        setHorizontallyScrolling(true);

        // 使用LinearInterpolator进行稳定滚动
        mSlr = new Scroller(this.getContext(), new LinearInterpolator());
        setScroller(mSlr);

        int scrollingLen = calculateScrollingLen();
        int distance = scrollingLen - (getWidth() + mXPaused);
        int duration = (new Double(mRndDuration * distance * 1.00000
                / scrollingLen)).intValue();

        mSlr.startScroll(mXPaused, 0, distance, 0, duration);
        invalidate();
        mPaused = false;
    }

    /**
     * 计算像素中文本的滚动长度
     *
     * @return 滚动长度（以像素为单位）
     */
    private int calculateScrollingLen() {
        TextPaint tp = getPaint();
        Rect rect = new Rect();
        String strTxt = getText().toString();
        tp.getTextBounds(strTxt, 0, strTxt.length(), rect);
        int scrollingLen = rect.width() + getWidth();
        rect = null;
        return scrollingLen;
    }

    /**
     * 暂停滚动文本
     */
    public void pauseScroll() {
        if (null == mSlr)
            return;

        if (mPaused)
            return;

        mPaused = true;

        // abortAnimation将当前X设置为最终X，
        //并设置isFinished为true
        //所以保存当前位置
        mXPaused = mSlr.getCurrX();

        mSlr.abortAnimation();
    }


    /**
     * 重写computeScroll以在完成时重新开始滚动
     * 文本永远滚动
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (null == mSlr) return;
        if (mSlr.isFinished() && (!mPaused)) {
            this.startScroll();
        }

        int mslr_startx = mSlr.getStartX();
        int mslr_currx = mSlr.getCurrX();
        int mslr_finalx = mSlr.getFinalX();
//        int aa = mSlr2.getStartX();
//        int bb = mSlr2.getCurrX();
//        int cc = mSlr2.getFinalX();
        LogUtils.i("computeScroll",
                "mslr_startx----->" + mslr_startx,
                "mslr_currx----->" + mslr_currx,
                "mslr_finalx----->" + mslr_finalx);
//                "StartX--aa--->" + aa,
//                "CurrX--bb--->" + bb,
//                "FinalX--cc--->" + cc);
    }

    public int getRndDuration() {
        return mRndDuration;
    }

    public void setRndDuration(int duration) {
        this.mRndDuration = duration;
    }

    public boolean isPaused() {
        return mPaused;
    }


}
