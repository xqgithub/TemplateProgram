package example.com.templateprogram.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.appevents.AppEventsLogger;

import example.com.templateprogram.R;
import example.com.templateprogram.runtimepermissions.PermissionsManager;
import example.com.templateprogram.runtimepermissions.PermissionsResultAction;
import example.com.templateprogram.test.activity.TestMainActivity;
import example.com.templateprogram.utils.PermissionsChecker;

/**
 * Created by XQ on 2017/4/15.
 * 检查用户是否具有相应的权限页面
 */
public class CheckPermissionsActivity extends Activity {


    private Activity mActivity;

    private static final int REQUEST_CODE = 0; // 请求码


    private AppEventsLogger logger;

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpermissions);
        //解决app点击桌面图标每次重新启动
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                }
            }
        }
        initView();
        initData();

        logger = AppEventsLogger.newLogger(CheckPermissionsActivity.this);
        logSentFriendRequestEvent();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        mActivity = CheckPermissionsActivity.this;
    }

    /**
     * 初始化数据
     */

    public void initData() {
        mPermissionsChecker = new PermissionsChecker(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        } else {
            Intent intent = new Intent(CheckPermissionsActivity.this,
                    TestMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }


    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else {
            Intent intent = new Intent(CheckPermissionsActivity.this,
                    TestMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }


    /**
     * This function assumes logger is an instance of AppEventsLogger and has been
     * created using AppEventsLogger.newLogger() call.
     */
    public void logSentFriendRequestEvent() {
        logger.logEvent("sentFriendRequest");
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
