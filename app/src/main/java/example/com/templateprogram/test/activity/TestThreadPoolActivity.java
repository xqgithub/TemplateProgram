package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.ThreadPoolUtils;

/**
 * Created by Administrator on 2019/9/11.
 * 测试线程池
 */

public class TestThreadPoolActivity extends BaseActivity {


    @BindView(R.id.btn_fixedthread)
    Button btnFixedthread;
    @BindView(R.id.btn_singlethread)
    Button btnSinglethread;
    @BindView(R.id.btn_ScheduledThread)
    Button btnScheduledThread;
    @BindView(R.id.btn_cachedthread)
    Button btnCachedthread;

    private ThreadPoolUtils poolUtils;

    private int num = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_threadpool;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_fixedthread, R.id.btn_singlethread, R.id.btn_ScheduledThread,
            R.id.btn_cachedthread})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fixedthread:
                List<Runnable> list1 = new ArrayList<>();
                list1.add(myRunnable);
                list1.add(myRunnable);

                poolUtils = new ThreadPoolUtils(ThreadPoolUtils.Type.FixedThread, 2);
                poolUtils.execute(list1);
                break;
            case R.id.btn_singlethread:
                List<Runnable> list2 = new ArrayList<>();
                list2.add(myRunnable);
                list2.add(myRunnable);

                poolUtils = new ThreadPoolUtils(ThreadPoolUtils.Type.SingleThread, 0);
                poolUtils.execute(list2);
                break;
            case R.id.btn_ScheduledThread:
                List<Runnable> list3 = new ArrayList<>();
                list3.add(myRunnable);
                list3.add(myRunnable);

                poolUtils = new ThreadPoolUtils(ThreadPoolUtils.Type.ScheduledThread, 1);
                poolUtils.execute(list3);
                break;
            case R.id.btn_cachedthread:
                List<Runnable> list4 = new ArrayList<>();
                list4.add(myRunnable);
                list4.add(myRunnable);

                poolUtils = new ThreadPoolUtils(ThreadPoolUtils.Type.CachedThread, 0);
                poolUtils.execute(list4);
                break;
        }
    }

    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 1; i < 6; i++) {
                try {
                    num = i;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LogUtils.i("myRunnable =-= " + num);
        }
    };


}
