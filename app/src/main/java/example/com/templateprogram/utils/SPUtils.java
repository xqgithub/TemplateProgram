package example.com.templateprogram.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : SP相关工具类
 * </pre>
 */
public class SPUtils {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private boolean encryptswitch = true;


    /**
     * SPUtils构造函数
     * <p>在Application中初始化</p>
     *
     * @param spName spName
     */
    public SPUtils(String spName) {
        sp = Utils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.apply();
    }

    /**
     * SP中写入String类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, @Nullable String value) {
        editor.putString(key, value).apply();
        if (encryptswitch) {
            encrypt(key, value);
        } else {
            editor.putString(key, value).apply();
        }
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public String getString(String key, String defaultValue) {
        if (encryptswitch && whetherEncryptData(key, defaultValue)) {
            return (String) decrypt(key, defaultValue);
        } else {
            return sp.getString(key, defaultValue);
        }
    }

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, int value) {
        if (encryptswitch) {
            encrypt(key, value);
        } else {
            editor.putInt(key, value).apply();
        }
    }

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public int getInt(String key, int defaultValue) {
        if (encryptswitch && whetherEncryptData(key, defaultValue)) {
            return (int) decrypt(key, defaultValue);
        } else {
            return sp.getInt(key, defaultValue);
        }
    }

    /**
     * SP中写入long类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, long value) {
        if (encryptswitch) {
            encrypt(key, value);
        } else {
            editor.putLong(key, value).apply();
        }
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public long getLong(String key) {
        return getLong(key, -1L);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public long getLong(String key, long defaultValue) {
        if (encryptswitch && whetherEncryptData(key, defaultValue)) {
            return (long) decrypt(key, defaultValue);
        } else {
            return sp.getLong(key, defaultValue);
        }
    }

    /**
     * SP中写入float类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, float value) {
        if (encryptswitch) {
            encrypt(key, value);
        } else {
            editor.putFloat(key, value).apply();
        }
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public float getFloat(String key) {
        return getFloat(key, -1f);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public float getFloat(String key, float defaultValue) {
        if (encryptswitch && whetherEncryptData(key, defaultValue)) {
            return (float) decrypt(key, defaultValue);
        } else {
            return sp.getFloat(key, defaultValue);
        }
    }

    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, boolean value) {
        if (encryptswitch) {
            encrypt(key, value);
        } else {
            editor.putBoolean(key, value).apply();
        }
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        if (encryptswitch && whetherEncryptData(key, defaultValue)) {
            return (boolean) decrypt(key, defaultValue);
        } else {
            return sp.getBoolean(key, defaultValue);
        }


    }

    /**
     * SP中写入String集合类型value
     *
     * @param key    键
     * @param values 值
     */
    public void put(String key, @Nullable Set<String> values) {
        editor.putStringSet(key, values).apply();
    }

    /**
     * SP中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    public Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }

    /**
     * SP中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public Set<String> getStringSet(String key, @Nullable Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public void remove(String key) {
        editor.remove(key).apply();
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * SP中清除所有数据
     */
    public void clear() {
//        LogUtils.i("----->我正在清楚XML文件");
        editor.clear().commit();
    }

    /**
     * 加密操作
     */
//    private String key_Integer = "=_=Integer";
//    private String key_Long = "=_=Long";
//    private String key_Boolean = "=_=Boolean";
//    private String key_Float = "=_=Float";
//    private String key_String = "=_=String";
    private void encrypt(String key, Object values) {
        String result = "";
        try {
            result = String.valueOf(values);
            result = AESCrypt.encrypt(StaticStateUtils.key, result);
            editor.putString(key, result).apply();
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }

    /**
     * 解密操作
     */
    private Object decrypt(String key, Object defaultValue) {
        Object result = null;
        try {
            String encryptvalues = sp.getString(key, String.valueOf(defaultValue));
            if (defaultValue instanceof Integer) {
                result = Integer.parseInt(AESCrypt.decrypt(StaticStateUtils.key, encryptvalues));
            } else if (defaultValue instanceof Long) {
                result = Long.parseLong(AESCrypt.decrypt(StaticStateUtils.key, encryptvalues));
            } else if (defaultValue instanceof Boolean) {
                result = Boolean.parseBoolean(AESCrypt.decrypt(StaticStateUtils.key, encryptvalues));
            } else if (defaultValue instanceof Float) {
                result = Float.parseFloat(AESCrypt.decrypt(StaticStateUtils.key, encryptvalues));
            } else if (defaultValue instanceof String) {
                result = AESCrypt.decrypt(StaticStateUtils.key, encryptvalues);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return result;
    }

    /**
     * 判断数据是否是已经加密过的数据
     */
    private boolean whetherEncryptData(String key, Object defaultValue) {
        boolean is_encrypt_data = false;
        try {
            String encryptvalues = sp.getString(key, String.valueOf(defaultValue));
            if (encryptvalues.endsWith("=") || encryptvalues.endsWith("==")) {
                is_encrypt_data = true;
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return is_encrypt_data;
    }
}