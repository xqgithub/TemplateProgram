package example.com.templateprogram.test.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by XQ on 2018/5/26.
 * 滑动控件
 */

public class SlipSwitch extends View implements View.OnTouchListener {

    // 开关开启时的背景，关闭时的背景，滑动按钮
    private Bitmap switch_on_bg, switch_off_bg, slip_Btn;
    // 是否正在滑动
    private boolean isSlipping = false;
    // 当前开关状态，true为开启，false为关闭
    private boolean isSwitchOn = false;
    // 手指按下时的水平坐标X，当前的水平坐标X
    private float previousX, currentX;
    // 开关监听器
    private OnSwitchListener onSwitchListener;
    // 是否设置了开关监听器
    private boolean isSwitchListenerOn = false;
    // 矩阵
    private Matrix matrix = new Matrix();
    // 画笔
    private Paint paint = new Paint();
    // 滑动按钮的左边坐标
    private float left_SlipBtn;
    // 松开前开关的状态
    private boolean previousSwitchState;

    public SlipSwitch(Context context) {
        super(context);
        init();
    }

    public SlipSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //初始化
    protected void init() {
        setOnTouchListener(this);
        setSwitchState(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 手指滑动到左半边的时候表示开关为关闭状态，滑动到右半边的时候表示开关为开启状态
        if (currentX < (switch_on_bg.getWidth() / 2)) {
            canvas.drawBitmap(switch_off_bg, matrix, paint);
        } else {
            canvas.drawBitmap(switch_on_bg, matrix, paint);
        }

        // 判断当前是否正在滑动
        if (isSlipping) {
            if (currentX > switch_on_bg.getWidth()) {
                left_SlipBtn = switch_on_bg.getWidth() - slip_Btn.getWidth();
            } else {
                left_SlipBtn = currentX - slip_Btn.getWidth() / 2;
            }
        } else {
            // 根据当前的开关状态设置滑动按钮的位置
            if (isSwitchOn) {
                left_SlipBtn = switch_off_bg.getWidth();
            } else {
                left_SlipBtn = 0;
            }
        }

        // 对滑动按钮的位置进行异常判断
        if (left_SlipBtn < 0) {
            left_SlipBtn = 0;
        } else if (left_SlipBtn > switch_on_bg.getWidth() - slip_Btn.getWidth()) {
            left_SlipBtn = switch_on_bg.getWidth() - slip_Btn.getWidth();
        }

        canvas.drawBitmap(slip_Btn, left_SlipBtn, 0, paint);
    }

    // 告诉父控件，要占多大的空间
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switch_on_bg.getWidth(), switch_on_bg.getHeight());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            // 滑动
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            // 按下
            case MotionEvent.ACTION_DOWN:
                isSlipping = true;
                previousX = event.getX();
                currentX = previousX;
                break;
            // 松开
            case MotionEvent.ACTION_UP:
                isSlipping = false;
                previousSwitchState = isSwitchOn;
//                if (event.getX() >= (switch_on_bg.getWidth() / 2)) {
//                    isSwitchOn = true;
//                } else {
//                    isSwitchOn = false;
//                }
                if (isSwitchOn) {
                    isSwitchOn = false;
                } else {
                    isSwitchOn = true;
                }
                // 如果设置了监听器，则调用此方法
                if (isSwitchListenerOn && (previousSwitchState != isSwitchOn)) {
                    onSwitchListener.onSwitched(isSwitchOn);
                }
                break;
        }
        // 重新绘制控件
        invalidate();
        return true;
    }

    public void setImageResource(int switchOnBkg, int switchOffBkg,
                                 int slipBtn) {
        switch_on_bg = BitmapFactory
                .decodeResource(getResources(), switchOnBkg);
        switch_off_bg = BitmapFactory.decodeResource(getResources(),
                switchOffBkg);
        slip_Btn = BitmapFactory.decodeResource(getResources(), slipBtn);

    }

    protected void setSwitchState(boolean switchState) {
        isSwitchOn = switchState;
    }

    protected boolean getSwitchState() {
        return isSwitchOn;
    }

    protected void updateSwitchState(boolean switchState) {
        isSwitchOn = switchState;
        invalidate();
    }

    public void setOnSwitchListener(OnSwitchListener listener) {
        onSwitchListener = listener;
        isSwitchListenerOn = true;
    }

    // 监听器接口
    public interface OnSwitchListener {
        abstract void onSwitched(boolean isSwitchOn);
    }
}
