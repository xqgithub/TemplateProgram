package example.com.templateprogram.utils.apiencrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import example.com.templateprogram.utils.DeviceUtils;
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
        int fi = 1;
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        String key = "";
        Map<String, Object> map = new HashMap<>();
        try {
            Random random = new Random();
            //key的类型,用来表示是第几套公式
            fi = 2;
//            fi = random.nextInt(3) + 1;
            if (fi == 1) {//公式1，这个值是写死的，后台定义
                key = Constants.keydefaults;
            } else if (fi == 2) {//公式2,key=mod(p1^3/p2^3)-p1/2
//                p1 = 777;
//                p2 = 469;
                p1 = random.nextInt(1000) + 1;
                p2 = random.nextInt(1000) + 1;
                double a = Math.pow(p1, 3) / Math.pow(p2, 3);
                double b = p1 / 2.0;
                double c = a - b;
                int result = (int) Math.floor(c);
                key = String.valueOf(result);
            } else if (fi == 3) {//公式3，key=p1+p2+p3^2
                p1 = random.nextInt(1000) + 1;
                p2 = random.nextInt(1000) + 1;
                p3 = random.nextInt(1000) + 1;
                int p3x2 = (int) Math.pow(p3, 2);
                int result = p1 + p2 + p3x2;
                key = String.valueOf(result);
            }
            String uuid = DeviceUtils.getUUID();
            map.put(Constants.fi, fi);
            map.put(Constants.p1, p1);
            map.put(Constants.p2, p2);
            map.put(Constants.p3, p3);
            map.put(Constants.uuid, uuid);
            key = uuid + key;
            map.put(Constants.key, key);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return map;
    }

    /**
     * 加密，得到加密后的值
     */
    public Map<String, Object> encrypt(String apiurl, Map<String, Object> parametermap) {
        String encryptmessage = "";
        Map<String, Object> map = new HashMap<>();
        try {
            if (!StringUtils.isBlank(parametermap) &&
                    parametermap.size() > 0) {
                StringBuffer stringbuffer = new StringBuffer();
                stringbuffer.append("?");
                for (Map.Entry<String, Object> entry : parametermap.entrySet()) {
//                    LogUtils.i("=-=", "key: " + entry.getKey(), "value: " + entry.getValue());
                    stringbuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                String result = stringbuffer.toString();
                result = result.substring(0, stringbuffer.toString().lastIndexOf("&"));
                encryptmessage = apiurl + result;
            } else {
                encryptmessage = apiurl;
            }
            map = getPasswordKey();
            if (!StringUtils.isBlank(map) && map.size() > 0) {
                String key = map.get(Constants.key).toString();
                encryptmessage = AESCBCCrypt.encrypt(key, encryptmessage);
                map.put(Constants.encryptmessage, encryptmessage);
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
