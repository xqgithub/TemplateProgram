package example.com.templateprogram.utils.apiencrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.StringUtils;

/**
 * Created by beijixiong on 2019/1/2.
 * api加密工具类
 */

public class APIEncryptUtils {

    private static volatile APIEncryptUtils mInstance;

    /**
     * 单例模式
     */
    public static APIEncryptUtils getInstance() {
        if (mInstance == null) {
            synchronized (APIEncryptUtils.class) {
                if (mInstance == null) {
                    mInstance = new APIEncryptUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取加密所需要的key值
     */
    public Map<String, Object> getPasswordKey() {
        int keytype = 1;
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        String key = "";
        Map<String, Object> map = new HashMap<>();
        try {
            Random random = new Random();
            //key的类型,用来表示是第几套公式
            keytype = random.nextInt(3) + 1;
            if (keytype == 1) {//公式1，这个值是写死的，后台定义
                key = Constants.keydefaults;
            } else if (keytype == 2) {//公式2,key=mod(p1^3/p2^3)-p1/2
                p1 = random.nextInt(1000) + 1;
                p2 = random.nextInt(1000) + 1;
                int p1x3 = (int) Math.pow(p1, 3);
                int p2x3 = (int) Math.pow(p2, 3);
                key = String.valueOf((p1x3 / p2x3 - p1 / 2));
            } else if (keytype == 3) {//公式3，key=p1+p2+p3^2
                p1 = random.nextInt(1000) + 1;
                p2 = random.nextInt(1000) + 1;
                p3 = random.nextInt(1000) + 1;
                int p3x2 = (int) Math.pow(p3, 2);
                key = String.valueOf(p1 + p2 + p3x2);
            }
            map.put(Constants.keytype, keytype);
            map.put(Constants.p1, p1);
            map.put(Constants.p2, p2);
            map.put(Constants.p3, p3);
            map.put(Constants.key, key);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return map;
    }

    /**
     * 加密，得到加密后的值
     */
    public Map<String, Object> encrypt(String message) {
        String encryptmessage = "";
        Map<String, Object> map = new HashMap<>();
        try {
            map = getPasswordKey();
            if (!StringUtils.isBlank(map) && map.size() > 0) {
                String key = map.get(Constants.key).toString();
                encryptmessage = AESCBCCrypt.encrypt(key, message);
                map.put(Constants.encryptmessage, encryptmessage);
            } else {

            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return map;
    }

    /**
     * 解密
     */
    public String decrypt(String key, String encryptmessage) {
        String result = "";
        try {
            result = AESCBCCrypt.decrypt(key, encryptmessage);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return result;
    }

}
