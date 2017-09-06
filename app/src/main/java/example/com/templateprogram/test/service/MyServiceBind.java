package example.com.templateprogram.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import example.com.templateprogram.utils.LogUtils;

/**
 * Created by admin on 2017/8/8.
 */
public class MyServiceBind extends Service {

    public static final String TAG = "MyServiceBind";


    private IBinder iBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i(TAG, "----->onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i(TAG, "----->onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBinder extends Binder {
        public MyServiceBind getService() {
            return MyServiceBind.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "----->onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.i(TAG, "----->onBind");
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.i(TAG, "----->onUnbind");
        return super.onUnbind(intent);
    }


    public void startDownload() {
        // 执行具体的下载任务
        LogUtils.i(TAG, "----->startDownload");
    }
}

