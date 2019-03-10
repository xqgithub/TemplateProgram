package example.com.templateprogram.test.myclass;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

import example.com.templateprogram.test.dialog.GlobalDialog;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.StringUtils;

/**
 * Created by beijixiong on 2019/3/10.
 * 公共实用方法类（一些需要公共的方法）
 */

public class PublicPracticalMethod {

    private static volatile PublicPracticalMethod mInstance;

    public static PublicPracticalMethod getInstance() {
        if (mInstance == null) {
            synchronized (PublicPracticalMethod.class) {
                if (mInstance == null) {
                    mInstance = new PublicPracticalMethod();
                }
            }
        }
        return mInstance;
    }


    /**
     * 判断应用是否在前台
     */
    public boolean isActive1 = true;

    public boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得顶部Activity名称
     */
    public String getTopActivity(Activity activity) {
        String top_name = "";
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        top_name = info.topActivity.getShortClassName();    //类名
        LogUtils.i("top_name----->" + top_name);
        return top_name;
    }


    /**
     * 全局弹框
     */
    public void showGlobalDialog(Activity mActivity) {
        if (StringUtils.isBlank(mActivity)) {
            return;
        }
        GlobalDialog globalDialog = new GlobalDialog(mActivity);
        globalDialog.show();
    }
}
