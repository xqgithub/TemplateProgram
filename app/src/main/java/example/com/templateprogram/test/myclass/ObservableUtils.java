package example.com.templateprogram.test.myclass;

import example.com.templateprogram.entity.ApiResponse;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by beijixiong on 2019/3/29.
 * Observable 一些封装方法
 */

public class ObservableUtils {

    private static volatile ObservableUtils mInstance;

    public static ObservableUtils getInstance() {
        if (mInstance == null) {
            synchronized (ObservableUtils.class) {
                if (mInstance == null) {
                    mInstance = new ObservableUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 对网络接口返回的ApiResponse进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final ApiResponse<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (response.isSuccess()) {
                    if (!subscriber.isUnsubscribed()) {//没有取消订阅
                        subscriber.onNext(response.getData());
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onError(new ApiGenerator.APIException(response.getError_code(), response.getMessage()));
                    }
                    return;
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> apiResponseObservable) {
                return apiResponseObservable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
