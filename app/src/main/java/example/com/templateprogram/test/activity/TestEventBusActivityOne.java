package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.events.MessageEvent;
import example.com.templateprogram.utils.StaticStateUtils;

/**
 * Created by beijixiong on 2018/12/1.
 * EventBus 的使用
 */

public class TestEventBusActivityOne extends BaseActivity {

    private Activity mActivity;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = TestEventBusActivityOne.this;
        EventBus.getDefault().register(this);
        tv = findViewById(R.id.tv);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_eventbus1;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                StaticStateUtils.intentToJump(mActivity, TestEventBusActivityTwo.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
        }
    }

    /**
     * 1.ThreadMode.POSTING:如果使用事件处理函数指定了线程模型为PostThread，那么该事件在哪个线程发布出来的，事件处理函数就会在这个线程中运行，也就是说发布事件和接收事件在同一个线程
     * 2.ThreadMode.MAIN:如果使用事件处理函数指定了线程模型为MainThread，那么不论事件是在哪个线程中发布出来的，该事件处理函数都会在UI线程中执行
     * 3.ThreadMode.BACKGROUND:如果使用事件处理函数指定了线程模型为BackgroundThread，那么如果事件是在UI线程中发布出来的，那么该事件处理函数就会在新的线程中运行，如果事件本来就是子线程中发布出来的，那么该事件处理函数直接在发布事件的线程中执行。在此事件处理函数中禁止进行UI更新操作
     * 4.ThreadMode.ASYNC:如果使用事件处理函数指定了线程模型为Async，那么无论事件在哪个线程发布，该事件处理函数都会在新建的子线程中执行。同样，此事件处理函数中禁止进行UI更新操作。
     *
     * @param event
     */

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void onEventMainThread(MessageEvent event) {
        String msg = "TestEventBusActivityOne收到了消息：" + event.getMessage();
        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread2(MessageEvent event) {
        String msg = "小雀雀：" + event.getMessage();
        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
