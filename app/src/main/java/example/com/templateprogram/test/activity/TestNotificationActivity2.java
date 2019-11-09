package example.com.templateprogram.test.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.notification.NotificationUtils;

/**
 * Created by Administrator on 2019/11/9.
 */

public class TestNotificationActivity2 extends BaseActivity implements View.OnClickListener {

    private NotificationManager mNotificationManager;


    //清除所有的通知
    private TextView tv1;
    // 发送通知1
    private TextView tv2;
    //发送通知2
    private TextView tv3;
    //左右滑动不删除
    private TextView tv4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_notification2;
    }

    private void init() {
        initView();
        initListener();
    }

    private void initView() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
    }

    private void initListener() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
    }

    private void initNotificationManager() {
        // 创建一个NotificationManager的引用
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                cancelAllNotification();
                break;
            case R.id.tv2:
                sendNotification1();
                break;
            case R.id.tv3:
                sendNotification2();
                break;
            case R.id.tv4:
                sendNotification3();
                break;
        }
    }


    /**
     * 清除所有通知
     */
    private void cancelAllNotification() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.clearNotification();
    }

    /**
     * 发送一个普通的通知
     */
    private void sendNotification1() {
        //这三个属性是必须要的，否则异常
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification(1, "这个是标题", "这个是内容", R.mipmap.ic_launcher);
    }


    private void sendNotification2() {
        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestNotificationActivity3.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what", 2);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 2, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 定义Notification的各种属性
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setContentIntent(resultPendingIntent)
                .sendNotificationCompat(2, "这个是标题2", "这个是内容2", R.mipmap.ic_launcher);
    }


    private void sendNotification3() {
        long[] vibrate = new long[]{0, 500, 1000, 1500};
        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestNotificationActivity3.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what", 3);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 3, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //发送pendingIntent

        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                //设置内容点击处理intent
                .setContentIntent(resultPendingIntent)
                //设置状态栏的标题
                .setTicker("来通知消息啦")
                //设置自定义view通知栏布局
                .setContent(getRemoteViews())
                //设置sound
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //自定义震动效果
                .setVibrate(vibrate)
                //必须设置的属性，发送通知
                .sendNotification(3, "这个是标题3", "这个是内容3", R.mipmap.ic_launcher);
    }

    /**
     * 自定义布局栏
     *
     * @return
     */
    private RemoteViews getRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_mobile_play);
        // 设置 点击通知栏的上一首按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getActivityPendingIntent(11));
        // 设置 点击通知栏的下一首按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_next, getActivityPendingIntent(12));
        // 设置 点击通知栏的播放暂停按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_start, getActivityPendingIntent(13));
        // 设置 点击通知栏的根容器时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.ll_root, getActivityPendingIntent(14));
        remoteViews.setTextViewText(R.id.tv_title, "标题");     // 设置通知栏上标题
        remoteViews.setTextViewText(R.id.tv_artist, "艺术家");   // 设置通知栏上艺术家
        return remoteViews;
    }

    /**
     * 获取一个Activity类型的PendingIntent对象
     */
    private PendingIntent getActivityPendingIntent(int what) {
        Intent intent = new Intent(this, TestNotificationActivity3.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        intent.putExtra("what", what);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, what, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }


}
