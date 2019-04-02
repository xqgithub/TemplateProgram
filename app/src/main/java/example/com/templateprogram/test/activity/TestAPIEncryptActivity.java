package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.entity.ApiResponse;
import example.com.templateprogram.http.ApiService;
import example.com.templateprogram.http.BaseSubscriber;
import example.com.templateprogram.http.RetrofitServiceManager;
import example.com.templateprogram.test.myclass.ObservableUtils;
import example.com.templateprogram.utils.AppUtils;
import example.com.templateprogram.utils.DeviceUtils;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.NetworkUtils;
import example.com.templateprogram.utils.PhoneUtils;
import example.com.templateprogram.utils.StringUtils;
import example.com.templateprogram.utils.apiencrypt.AESCBCCrypt;
import example.com.templateprogram.utils.apiencrypt.APIEncryptUtils;
import example.com.templateprogram.utils.apiencrypt.Constants;
import example.com.templateprogram.utils.apiencrypt.LocationResponse;
import example.com.templateprogram.utils.apiencrypt.SigninResponseV2;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by beijixiong on 2019/1/2.
 */

public class TestAPIEncryptActivity extends BaseActivity {

    private Activity mActivity;
    private static final String TAG = "=-=";

    private static String key = "86712786e2205b50e80721462334364d";
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = findViewById(R.id.tv);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apiencrypt;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                try {
//                    Map<String, Object> map = APIEncryptUtils.getInstance().encrypt("我是海贼王路飞");
//                    if (map.size() > 0) {
//                        Object[] objects = {
//                                "fi:" + map.get(Constants.fi),
//                                "p1:" + map.get(Constants.p1),
//                                "p2:" + map.get(Constants.p2),
//                                "p3:" + map.get(Constants.p3),
//                                "key:" + map.get(Constants.key),
//                                "encryptmessage:" + map.get(Constants.encryptmessage),
//                                "解密后的值:" + APIEncryptUtils.getInstance().decrypt(map.get(Constants.key).toString(), map.get(Constants.encryptmessage).toString())};
//                        LogUtils.i(TAG, objects);
//                    }
                    AESCBCCrypt.encrypt("185", "/api/client/v1/signin?a=b");
                } catch (Exception e) {
                    LogUtils.e(e.getMessage());
                }

                break;
            case R.id.btn2:
//                testApi();
                testApiLogin();
                break;
        }
    }


    /**
     * 请求api测试
     */
    public void testApi() {
//        String baseurl = "https://api.lskdjsmc.com/";
//        String parturl = "api/client/v1/commons/location";
        String baseurl = "http://192.168.3.11/";
        String parturl = "api/client/v1/commons/location";
        Map<String, Object> map = APIEncryptUtils.getInstance().encrypt(parturl, null);
        if (map.size() > 0) {
            Object[] objects = {
                    "fi:" + map.get(Constants.fi),
                    "p1:" + map.get(Constants.p1),
                    "p2:" + map.get(Constants.p2),
                    "p3:" + map.get(Constants.p3),
                    "uuid:" + map.get(Constants.uuid),
                    "key:" + map.get(Constants.key),
                    "encryptmessage:" + map.get(Constants.encryptmessage),
                    "解密后的值:" + APIEncryptUtils.getInstance().decrypt(map.get(Constants.key).toString(), map.get(Constants.encryptmessage).toString())};
            LogUtils.i(TAG, objects);
        }
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
//        apiService.location(
//                baseurl + map.get(Constants.encryptmessage).toString(),
//                map.get(Constants.fi).toString(),
//                map.get(Constants.p1).toString(),
//                map.get(Constants.p2).toString(),
//                map.get(Constants.p3).toString(),
//                DeviceUtils.getUUID())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<ApiResponse<LocationResponse>>() {
//                    @Override
//                    public void onSuccess(ApiResponse<LocationResponse> locationResponseApiResponse) {
//
//                    }
//
//                    @Override
//                    public void onFailure(String message, int error_code) {
//
//                    }
//                });
        apiService.location(baseurl + map.get(Constants.encryptmessage).toString(),
                map.get(Constants.fi).toString(),
                map.get(Constants.p1).toString(),
                map.get(Constants.p2).toString(),
                map.get(Constants.p3).toString(),
                DeviceUtils.getUUID())
                .compose(ObservableUtils.getInstance().<ApiResponse<LocationResponse>>applySchedulers())
                .subscribe(new BaseSubscriber<ApiResponse<LocationResponse>>() {
                    @Override
                    public void onSuccess(ApiResponse<LocationResponse> locationResponseApiResponse) {

                    }

                    @Override
                    public void onFailure(String message, int error_code) {

                    }
                });
    }

    public void testApiLogin() {
        String baseurl = "http://192.168.3.11/";
        String parturl = "/api/client/v4/signin";
        Map<String, Object> loginOptions = new HashMap<>();
        loginOptions.put("device_uuid", DeviceUtils.getUUID());                   // 设备uuid
        loginOptions.put("device_name", DeviceUtils.getModel());                           // 设备名(如: xxx的iphone)
        loginOptions.put("device_model", DeviceUtils.getModel());                 // 设备型号(如: iphone6)
        loginOptions.put("platform", "android");                                  // 设备系统平台(如: ios)
        loginOptions.put("system_version", Build.VERSION.RELEASE);                // 设备版本(如: 10)
        loginOptions.put("operator", PhoneUtils.getSimOperatorByMnc());           // 网络运营商(如: unicomm)
        loginOptions.put("net_env", NetworkUtils.getNetworkType());               // 网络环境(如: 4g、wifi)
        loginOptions.put("app_version", "nuts");        // 版本名，来自版本渠道信息接口获取到的ID
        loginOptions.put("app_version_number", AppUtils.getAppVersionName(this)); // App版本(如: 1.0)
        loginOptions.put("app_channel", "moren");                                // app渠道, 来自版本渠道信息接口获取到的ID
        loginOptions.put("sdid", StringUtils.isBlank(DeviceUtils.getUniqueDeviceID(mActivity)) ? "" : DeviceUtils.getUniqueDeviceID(mActivity));        //设备共享码
        loginOptions.put("apiid", "woshilucian");        //Android机型硬件api码
        Map<String, Object> map = APIEncryptUtils.getInstance().encrypt(parturl, loginOptions);
        if (map.size() > 0) {
            Object[] objects = {
                    "fi:" + map.get(Constants.fi),
                    "p1:" + map.get(Constants.p1),
                    "p2:" + map.get(Constants.p2),
                    "p3:" + map.get(Constants.p3),
                    "uuid:" + map.get(Constants.uuid),
                    "key:" + map.get(Constants.key),
                    "encryptmessage:" + map.get(Constants.encryptmessage),
                    "解密后的值:" + APIEncryptUtils.getInstance().decrypt(map.get(Constants.key).toString(), map.get(Constants.encryptmessage).toString())};
            LogUtils.i(TAG, objects);
        }
        Map<String, String> header_map = new HashMap<>();
        header_map.put(Constants.fi, map.get(Constants.fi).toString());
        header_map.put(Constants.p1, map.get(Constants.p1).toString());
        header_map.put(Constants.p2, map.get(Constants.p2).toString());
        header_map.put(Constants.p3, map.get(Constants.p3).toString());
        header_map.put(Constants.uuid, map.get(Constants.uuid).toString());

        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.signinv4(baseurl + map.get(Constants.encryptmessage).toString(), header_map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<SigninResponseV2>>() {
                    @Override
                    public void onSuccess(ApiResponse<SigninResponseV2> signinResponseV2ApiResponse) {

                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.e(TAG,
                                "message: " + message,
                                "error_code: " + error_code);
                    }
                });

    }


}
