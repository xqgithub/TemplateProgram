package example.com.templateprogram.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.ToastUtils;

/**
 * Created by admin on 2017/9/6.
 */
public class TestMessengerService extends Service {

    public static final String TAG = "TestMessengerService";

    public class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ToastUtils.showLongToast("hello, trampcr");
                    break;
            }
        }
    }


    Messenger mMessenger = new Messenger(new IncomingHandler());

    public IBinder onBind(Intent intent) {
        ToastUtils.showLongToast("binding");
        return mMessenger.getBinder();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i("----->onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("----->onDestroy");
    }
}
