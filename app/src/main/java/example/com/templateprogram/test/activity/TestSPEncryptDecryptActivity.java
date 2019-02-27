package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.constants.SPConstants;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.SPUtils;

/**
 * Created by beijixiong on 2019/2/26.
 * 测试保存sp数据加密解密操作
 */

public class TestSPEncryptDecryptActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testjiaMi();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_spencryptdecrypt;
    }

    private void testjiaMi() {
        SPUtils spUtils = new SPUtils(SPConstants.SP_TEST_JIAMI);

        String testString = "我是海贼王路飞";
        int testint = 77;
        long testlong = 30000000;
        float testfloat = 7.7f;
        boolean testboolean = true;
        spUtils.put(SPConstants.testjiami.test1, testString);
        spUtils.put(SPConstants.testjiami.test2, testint);
        spUtils.put(SPConstants.testjiami.test3, testlong);
        spUtils.put(SPConstants.testjiami.test4, testfloat);
        spUtils.put(SPConstants.testjiami.test5, testboolean);

         testString = spUtils.getString(SPConstants.testjiami.test1, "");
         testint = spUtils.getInt(SPConstants.testjiami.test2, 0);
         testlong = spUtils.getLong(SPConstants.testjiami.test3, 0L);
         testfloat = spUtils.getFloat(SPConstants.testjiami.test4, 0f);
         testboolean = spUtils.getBoolean(SPConstants.testjiami.test5, false);

        LogUtils.i("TestSPEncryptDecryptActivity",
                "testString =-=" + testString,
                "testint =-=" + testint,
                "testlong =-=" + testlong,
                "testfloat =-=" + testfloat,
                "testboolean =-=" + testboolean);
    }
}
