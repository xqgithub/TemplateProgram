package example.com.templateprogram.test.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.service.TestMessengerService;

/**
 * Created by admin on 2017/9/6.
 */
public class TestMessengerActivity extends BaseActivity implements View.OnClickListener {


    private Button mBtnAidl;

    private Messenger messenger;

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
        Intent intent = new Intent(this, TestMessengerService.class);
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
                if (!bingflag) {
                    return;
                }

                try {
                    Message message = Message.obtain();
                    message.what = 0;
                    messenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private boolean bingflag = false;
    ServiceConnection mserviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bingflag = true;
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bingflag = false;
            messenger = null;
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
