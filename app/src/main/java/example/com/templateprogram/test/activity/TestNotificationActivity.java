package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.view.NotificationHelper;

/**
 * Created by admin on 2018/7/16.
 */

public class TestNotificationActivity extends BaseActivity implements View.OnClickListener {


    private Activity mActivity;

    private static final int NOTIFY_PRIMARY1 = 1100;
    private static final int NOTIFY_PRIMARY2 = 1101;
    private static final int NOTIFY_SECONDARY1 = 1200;
    private static final int NOTIFY_SECONDARY2 = 1201;


    /**
     * Helper
     */
    private NotificationHelper mHelper;

    private EditText main_primary_title;
    private Button main_primary_send1;
    private Button main_primary_send2;
    private ImageButton main_primary_config;

    private EditText main_secondary_title;
    private Button main_secondary_send1;
    private Button main_secondary_send2;
    private ImageButton main_secondary_config;
    private Button btn_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        mHelper = new NotificationHelper(mActivity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_notification;
    }

    private void initViews() {
        mActivity = TestNotificationActivity.this;
        main_primary_title = findViewById(R.id.main_primary_title);
        main_primary_send1 = findViewById(R.id.main_primary_send1);
        main_primary_send2 = findViewById(R.id.main_primary_send2);
        main_primary_config = findViewById(R.id.main_primary_config);

        main_secondary_title = findViewById(R.id.main_secondary_title);
        main_secondary_send1 = findViewById(R.id.main_secondary_send1);
        main_secondary_send2 = findViewById(R.id.main_secondary_send2);
        main_secondary_config = findViewById(R.id.main_secondary_config);
        btn_setting = findViewById(R.id.btn_setting);


        main_primary_send1.setOnClickListener(this);
        main_primary_send2.setOnClickListener(this);
        main_primary_config.setOnClickListener(this);

        main_secondary_send1.setOnClickListener(this);
        main_secondary_send2.setOnClickListener(this);
        main_secondary_config.setOnClickListener(this);


        btn_setting.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_primary_send1:
                sendNotification(R.id.main_primary_send1,"海贼王路飞");
                break;
        }
    }

    public void sendNotification(int id, String title) {
        Notification.Builder builder = null;
        switch (id) {
            case R.id.main_primary_send1:
                builder = mHelper.getNotification1(title,"我要成为海贼王的男人啊");
                break;
        }

        if (mHelper != null) {
            mHelper.notify(id, builder);
        }
    }


}
