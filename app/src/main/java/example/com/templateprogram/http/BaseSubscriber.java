package example.com.templateprogram.http;

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
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onStart() {
        super.onStart();
        // show load dialog
    }


}
