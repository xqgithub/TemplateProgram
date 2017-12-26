package example.com.templateprogram.http;

import example.com.templateprogram.entity.ApiResponse;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.NetworkUtils;
import rx.Subscriber;

/**
 * Created by admin on 2017/6/20.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {


    public abstract void onSuccess(T t);

    public abstract void onFailure(String message, int error_code);

    @Override
    public void onCompleted() {
        // hide load dialog
        unsubscribe();
    }

    @Override
    public void onError(Throwable e) {
        try {
            LogUtils.i("Throwable---->" + e.getMessage().toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onNext(T t) {
        ApiResponse br = (ApiResponse) t;
        if (br == null) {
            onFailure("请求失败:" + 400, 400);
            return;
        }
        int error_code = 0;
        if (br.isSuccess()) {
            onSuccess(t);
        } else {
            error_code = br.getError_code();
            onFailure(br.getMessage(), error_code);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected()) {
            // 没有网络则取消订阅
            unsubscribe();
            onFailure("手机无网络", 800);
        }
    }


}
