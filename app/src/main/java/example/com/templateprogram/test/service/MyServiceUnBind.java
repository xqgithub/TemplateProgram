package example.com.templateprogram.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import example.com.templateprogram.utils.LogUtils;

/**
 * Created by admin on 2017/8/8.
 */
public class MyServiceUnBind extends Service {

    public static final String TAG = "MyServiceUnBind";


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

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "----->onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
