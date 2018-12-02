package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.events.MessageEvent;

/**
 * Created by beijixiong on 2018/12/1.
 * EventBus 的使用
 */

public class TestEventBusActivityTwo extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_eventbus2;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                EventBus.getDefault().post(new MessageEvent("我是船长，路飞"));
                finish();
                break;
        }
    }
}
