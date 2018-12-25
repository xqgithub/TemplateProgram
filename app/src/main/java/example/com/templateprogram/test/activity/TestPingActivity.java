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

import java.util.ArrayList;
import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.aidl.IPingAidlInterface;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.service.PingAidlService;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.StringUtils;

/**
 * Created by beijixiong on 2018/12/17.
 * 测试traceroute
 */

public class TestPingActivity extends BaseActivity {

    private Activity mActivity;
    private Button btn;
    private String url = "www.baidu.com";
    private IPingAidlInterface ipingaidlinterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = TestPingActivity.this;
        btn = findViewById(R.id.btn);
        getDatas();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTestAidl();
            }
        });
        initService();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_ping;
    }


    public void initService() {
        Intent intent = new Intent(mActivity, PingAidlService.class);
        bindService(intent, mserviceConnection, Context.BIND_AUTO_CREATE);
    }


    private boolean bingflag = false;
    ServiceConnection mserviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bingflag = true;
            ipingaidlinterface = IPingAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bingflag = false;
        }
    };

    public void toTestAidl() {
        if (!StringUtils.isBlank(ipingaidlinterface)) {
            try {
                for (int i = 0; i < mDatas.size(); i++) {
                    Bundle bundle = ipingaidlinterface.getPingTime2(mDatas.get(i));
//                    Thread.sleep(5000);
                    LogUtils.i("minPing =-= " + bundle.get("minPing"));
                    LogUtils.i("maxPing =-= " + bundle.get("maxPing"));
                    LogUtils.i("avgPing =-= " + bundle.get("avgPing"));
                    LogUtils.i("packetLoss =-= " + bundle.get("packetLoss"));
                }
            } catch (Exception e) {
                LogUtils.e(e.getMessage());
            }
        }
    }

    private List<String> mDatas;

    private void getDatas() {
        mDatas = new ArrayList<>();
        mDatas.add("www.baidu.com");
        mDatas.add("https://www.hao123.com/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bingflag) {
            unbindService(mserviceConnection);
        }
    }
}
