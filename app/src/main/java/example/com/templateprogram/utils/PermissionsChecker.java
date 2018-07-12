package example.com.templateprogram.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

/**
 * Created by XQ on 2017/4/15.
 * 检查权限的工具类
 */
public class PermissionsChecker {

    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        int flag = mContext.checkSelfPermission(permission);
        int flag1 = PermissionChecker.checkSelfPermission(mContext, permission);
        int flag2 = ActivityCompat.checkSelfPermission(mContext, permission);
        return PermissionChecker.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
