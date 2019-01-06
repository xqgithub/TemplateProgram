package example.com.templateprogram.test.activity;

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
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.apiencrypt.APIEncryptUtils;
import example.com.templateprogram.utils.apiencrypt.Constants;
import example.com.templateprogram.utils.apiencrypt.LocationResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by beijixiong on 2019/1/2.
 */

public class TestAPIEncryptActivity extends BaseActivity {

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
                    Map<String, Object> map = APIEncryptUtils.getInstance().encrypt("我是海贼王路飞");
                    if (map.size() > 0) {
                        Object[] objects = {
                                "keytype:" + map.get(Constants.keytype),
                                "p1:" + map.get(Constants.p1),
                                "p2:" + map.get(Constants.p2),
                                "p3:" + map.get(Constants.p3),
                                "key:" + map.get(Constants.key),
                                "encryptmessage:" + map.get(Constants.encryptmessage),
                                "解密后的值:" + APIEncryptUtils.getInstance().decrypt(map.get(Constants.key).toString(), map.get(Constants.encryptmessage).toString())};
                        LogUtils.i(TAG, objects);
                    }
                } catch (Exception e) {
                    LogUtils.e(e.getMessage());
                }

                break;
            case R.id.btn2:
                testApi();
                break;
        }
    }


    /**
     * 请求api测试
     */
    public void testApi() {
        String baseurl = "https://api.lskdjsmc.com/";
        String parturl = "api/client/v1/commons/location";
        Map<String, Object> field_map = new HashMap<>();
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.location(baseurl + parturl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<LocationResponse>>() {
                    @Override
                    public void onSuccess(ApiResponse<LocationResponse> locationResponseApiResponse) {

                    }

                    @Override
                    public void onFailure(String message, int error_code) {

                    }
                });
    }


}
