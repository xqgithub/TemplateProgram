package example.com.templateprogram.test.myclass;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import example.com.templateprogram.constants.ConfigConstants;
import example.com.templateprogram.test.dialog.GlobalDialog;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.StringUtils;
import example.com.templateprogram.utils.apiencrypt.APIEncryptUtils;
import example.com.templateprogram.utils.apiencrypt.Constants;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;

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

    /**
     * Okhttp retrofit 拦截器，
     * 1.修改参数
     * 2.添加请求头
     */
    public Request OkHttpInterceptorAPIEncrypt(Request request) {


        //1.从request中获取原有的HttpUrl实例oldHttpUrl
        HttpUrl oldHttpUrl = request.url();
//        LogUtils.i("OkHttpInterceptor",
//                "url =-=" + oldHttpUrl.url(),
//                "queryParameterNames =-=" + oldHttpUrl.queryParameterNames(),
//                "headers =-= " + request.headers());
        //2.获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        //3.先判断有没有参数
        Set<String> queryParameterNames = oldHttpUrl.queryParameterNames();
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isBlank(queryParameterNames) && queryParameterNames.size() > 0) {
            for (String names : queryParameterNames) {
                map.put(names, oldHttpUrl.queryParameter(names));
            }

        }
        //4.获取apiurl
        String apiurl = oldHttpUrl.url().getPath().replace(ConfigConstants.base_url, "");
        //5.给api加密
        Map<String, Object> encryptMap = APIEncryptUtils.getInstance().encrypt(apiurl, map);
        if (encryptMap.size() > 0) {
            Object[] objects = {
                    "fi:" + encryptMap.get(Constants.fi),
                    "p1:" + encryptMap.get(Constants.p1),
                    "p2:" + encryptMap.get(Constants.p2),
                    "p3:" + encryptMap.get(Constants.p3),
                    "uuid:" + encryptMap.get(Constants.uuid),
                    "key:" + encryptMap.get(Constants.key),
                    "encryptmessage:" + encryptMap.get(Constants.encryptmessage),
                    "解密后的值:" + APIEncryptUtils.getInstance().decrypt(encryptMap.get(Constants.key).toString(), encryptMap.get(Constants.encryptmessage).toString())};
            LogUtils.i("OkHttpInterceptorAPIEncrypt", objects);
        }
        //6.获取heads，修改heads
        Headers headers = request.headers();
        Headers newHeader = headers.newBuilder()
                .add(Constants.fi, encryptMap.get(Constants.fi).toString())
                .add(Constants.p1, encryptMap.get(Constants.p1).toString())
                .add(Constants.p2, encryptMap.get(Constants.p2).toString())
                .add(Constants.p3, encryptMap.get(Constants.p3).toString())
                .add(Constants.uuid, encryptMap.get(Constants.uuid).toString())
                .build();
        //7.转换后的new url
        HttpUrl newUrl = HttpUrl.parse(ConfigConstants.base_url + encryptMap.get(Constants.encryptmessage).toString());
        //8.生成新的Request
        Request requestnew = builder.url(newUrl)
                .headers(newHeader)
                .build();
        return requestnew;
    }


}
