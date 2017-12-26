package example.com.templateprogram.test.activity;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.entity.ApiResponse;
import example.com.templateprogram.entity.Member;
import example.com.templateprogram.http.ApiService;
import example.com.templateprogram.http.BaseSubscriber;
import example.com.templateprogram.http.RetrofitServiceManager;
import example.com.templateprogram.utils.LogUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by XQ on 2017/12/8.
 * 测试自己写的服务器端Json接口
 */
public class TestJsonResolve extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    /**
     * 加载UI
     */
    public void initView() {

    }

    /**
     * 初始化数据
     */
    public void initData() {
        testByAction();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jsonresolve;
    }


    /**
     * 测试请求
     */
    public void testByAction() {

        Map<String, String> options = new HashMap<>();
        options.put("id", "2");
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.testByAction(options)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<Member>>() {
                    @Override
                    public void onSuccess(ApiResponse<Member> memberApiResponse) {
                        if (memberApiResponse.isSuccess()) {
                            LogUtils.i("Name---->" + memberApiResponse.getData().getMember().getName());
                        } else {
                            LogUtils.i("Throwable---->" + memberApiResponse.toString());
                        }
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailure----->" + message + " | " + error_code);
                    }
                });
    }
}
