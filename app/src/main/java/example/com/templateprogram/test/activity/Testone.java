package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.service.MyServiceBind;
import example.com.templateprogram.test.service.MyServiceUnBind;
import example.com.templateprogram.utils.StringUtils;

/**
 * Created by admin on 2017/8/8.
 */
public class Testone extends BaseActivity implements View.OnClickListener {


    private Activity mActivity;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();


    }

    /**
     * 加载UI
     */
    public Intent serviceIntent;

    public void initView() {
        mActivity = Testone.this;
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
    }

    /**
     * 加载监听
     */
    public void initListener() {
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }


    /**
     * 加载布局文件
     *
     * @return 布局文件的ID
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_one;
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                serviceIntent = new Intent(Testone.this, MyServiceUnBind.class);
                startService(serviceIntent);
                break;
            case R.id.button2:
                if (!StringUtils.isBlank(serviceIntent)) {
                    stopService(serviceIntent);
                    serviceIntent = null;
                }
                break;
            case R.id.button3:
                Intent intent = new Intent(mActivity, MyServiceBind.class);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.button4:
                if (bingflag) {
                    unbindService(serviceConnection);
                }
                break;
        }
    }


    private MyServiceBind myServiceBind;
    private boolean bingflag = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bingflag = true;
            MyServiceBind.MyBinder myBinder = (MyServiceBind.MyBinder) service;
            myServiceBind = myBinder.getService();
            myServiceBind.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bingflag = false;
        }
    };
}
