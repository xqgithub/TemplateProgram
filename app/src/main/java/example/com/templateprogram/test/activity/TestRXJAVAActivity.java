package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CheckBox;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.bean.RxJavaStudents;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.ToastUtils;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by beijixiong on 2019/3/28.
 * RxJava
 * RXBinding
 */

public class TestRXJAVAActivity extends BaseActivity {


    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.checkBox)
    CheckBox checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TestA();
//        TestB();
//        TestC();
//        TestD();
        onClickRxBinding();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_rxjava;
    }

    private void TestA() {
        //1.观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
                LogUtils.i("Item: " + s);
            }

            @Override
            public void onCompleted() {
                LogUtils.i("Completed!");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i("Error!");
            }
        };
        //2.被观察者
        Observable observable = Observable.just("Hello", "Hi", "Aloha");
        //3.订阅,发送事件是在被订阅的时候
        observable.subscribe(observer);
    }

    private void TestB() {
        Observable observable = Observable.just("Hello", "Hi", "Aloha");

        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                LogUtils.i("Item: " + s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                LogUtils.i("Error!");
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                LogUtils.i("Completed!");
            }
        };

        //1. 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
//        observable.subscribe(onNextAction);
        //2. 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
//        observable.subscribe(onNextAction, onErrorAction);
        //3. 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }


    private void TestC() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i("Completed!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i("Error!");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtils.i("Item: " + integer);
                    }
                });
    }


    /**
     * 1.map() 是一对一的转化
     * 2.flatMap()一对多的转化
     * 3.flatMap() 也常用于嵌套的异步操作
     * 4.lift
     * (1).lift() 在 Observable 执行了 lift(Operator) 方法之后，会返回一个新的 Observable，这个新的 Observable 会像一个代理一样，负责接收原始的 Observable 发出的事件，并在处理后发送给 Subscriber
     * (2).建议尽量使用已有的 lift() 包装方法（如 map() flatMap() 等）进行组合来实现需求，因为直接使用 lift() 非常容易发生一些难以发现的错误
     */
    private void TestD() {
        List<RxJavaStudents.Course> courseslist = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RxJavaStudents.Course course = new RxJavaStudents.Course();
            if (i == 0) {
                course.setCoursename("Language");
            } else if (i == 1) {
                course.setCoursename("Mathematics");
            } else if (i == 2) {
                course.setCoursename("English");
            }
            courseslist.add(course);
        }
        RxJavaStudents students = new RxJavaStudents();
        students.setName("luxian");
        students.setAge(30);
        students.setCourses(courseslist);


        Subscriber<RxJavaStudents.Course> subscriber = new Subscriber<RxJavaStudents.Course>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxJavaStudents.Course course) {
                LogUtils.i("Item: " + course.getCoursename());
            }
        };

        RxJavaStudents[] rxJavaStudents = {students};
        Observable.from(rxJavaStudents).flatMap(new Func1<RxJavaStudents, Observable<RxJavaStudents.Course>>() {
            @Override
            public Observable<RxJavaStudents.Course> call(RxJavaStudents rxJavaStudents) {
                return Observable.from(rxJavaStudents.getCourses());
            }
        }).subscribe(subscriber);
    }

    /**
     * RxBinding点击事件
     */
    private void onClickRxBinding() {
        //1.防止连续点击
        RxView.clicks(btn1)
                .throttleFirst(2, TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
//                        LogUtils.i("两秒钟之内只取一个点击事件");
                        ToastUtils.showShortToast("两秒钟之内只取一个点击事件");
                    }
                });


        //2.监听长按事件
        RxView.longClicks(btn2)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
//                        LogUtils.i("btn2 =-= 被长按了啊");
                        ToastUtils.showShortToast("btn2 =-= 被长按了啊");
                    }
                });


        //3.勾选事件
        RxCompoundButton.checkedChanges(checkBox)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        btn1.setEnabled(aBoolean);
//                        ToastUtils.showShortToast("btn1 =-= 状态改变了");
                    }
                });
        //4.点击多次监听
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        Observable<Void> clickObservable = RxView.clicks(btn3).share();
        Subscription s1 = clickObservable.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                LogUtils.i("btn3 =-= 第一次点击");
            }
        });
        compositeSubscription.add(s1);
        Subscription s2 = clickObservable.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                LogUtils.i("btn3 =-= 第二次点击");
            }
        });
        compositeSubscription.add(s2);
    }
    /**
     * Backpressure 其实是一种现象：在数据流从上游生产者向下游消费者传输的过程中，上游生产速度大于下游消费速度，导致下游的 Buffer 溢出，这种现象就叫做 Backpressure 出现
     */
}
