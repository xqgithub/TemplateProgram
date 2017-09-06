package example.com.templateprogram.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import example.com.templateprogram.aidl.IMyAidlInterface;
import example.com.templateprogram.utils.LogUtils;

/**
 * Created by admin on 2017/9/6.
 */
public class TestAIDLService extends Service {

    public static final String TAG = "TestAIDLService";


    IMyAidlInterface.Stub mStub = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getName(String nickName) throws RemoteException {
            String nickname = "aidl-----" + nickName;
            return nickname;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i(TAG, "----->onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "----->onDestroy");
    }


}
