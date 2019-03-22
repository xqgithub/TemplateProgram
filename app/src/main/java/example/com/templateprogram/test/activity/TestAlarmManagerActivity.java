package example.com.templateprogram.test.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.constants.ConfigConstants;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.StringUtils;

/**
 * Created by beijixiong on 2019/3/10.
 * 全局定时器AlarmManager,通常用于开发手机闹钟
 * 现在作为一个全局计时器来做，AlarmMangager可在指定的时间或指定周期启动其他组件（包括Activity、Service、BroadcastReceiver）
 */

public class TestAlarmManagerActivity extends BaseActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;

    private AlarmManager alarmManager;
    PendingIntent pi;
    private long interval_time = 10 * 1000;//最小值是5秒

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_alarmmanager;
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Intent intent = new Intent();
                intent.setAction(ConfigConstants.action_alarm);
                pi = PendingIntent.getBroadcast(this,
                        0, intent, 0);
                alarmSendBroadcastReceiver(alarmManager, pi);
                break;
            case R.id.btn2:
                alarmManager.cancel(pi);
                break;
            case R.id.btn3:
//                alarmManager.cancel(pi);
                alarmSendBroadcastReceiver(alarmManager, pi);
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConfigConstants.action_alarm);
        registerReceiver(alarmmanager_receiver, intentFilter);
    }

    /**
     * alarm 发送广播
     */
    public void alarmSendBroadcastReceiver(AlarmManager alarmManager, PendingIntent pi) {

        if (!StringUtils.isBlank(alarmManager)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//api23,Android 6.0以上，解决在低电耗模式下的闹钟触发
                LogUtils.i("我开始调用了");
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + interval_time, pi);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//api19, Android 4.4以上
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval_time, pi);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval_time, pi);
            }
        }
    }


    private BroadcastReceiver alarmmanager_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.i("我被调用了");
            if (ConfigConstants.action_alarm.equals(intent.getAction())) {
                // 重复定时任务
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime() + interval_time, pi);

                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval_time, pi);
                }
            }
//            LogUtils.i("getAction =-=" + intent.getAction());
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!StringUtils.isBlank(alarmmanager_receiver)) {
            unregisterReceiver(alarmmanager_receiver);
        }
        if (!StringUtils.isBlank(alarmManager) && !StringUtils.isBlank(pi)) {
            alarmManager.cancel(pi);
        }
    }


}
