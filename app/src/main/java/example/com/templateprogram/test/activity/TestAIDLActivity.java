package example.com.templateprogram.test.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import example.com.templateprogram.R;
import example.com.templateprogram.aidl.IMyAidlInterface;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.service.TestAIDLService;
import example.com.templateprogram.utils.ToastUtils;

/**
 * Created by admin on 2017/9/6.
 */
public class TestAIDLActivity extends BaseActivity implements View.OnClickListener {


    private Button mBtnAidl;
    private IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    /**
     * 加载UI
     */

    public void initView() {
        mBtnAidl = (Button) findViewById(R.id.mBtnAidl);
    }

    /**
     * 加载监听
     */
    public void initListener() {
        mBtnAidl.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    public void initData() {
        Intent intent = new Intent(this, TestAIDLService.class);
        bindService(intent, mserviceConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_aidl;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtnAidl:
                if (iMyAidlInterface != null) {
                    try {
                        String name = iMyAidlInterface.getName("我是海贼王路飞");
                        ToastUtils.showLongToast(name);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private boolean bingflag = false;
    ServiceConnection mserviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bingflag = true;
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bingflag = false;
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bingflag) {
            unbindService(mserviceConnection);
        }
    }
}
