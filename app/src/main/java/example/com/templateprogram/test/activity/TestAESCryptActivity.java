package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.base.MyApp;
import example.com.templateprogram.utils.AESCrypt;
import example.com.templateprogram.utils.FileUtils;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.ToastUtils;

/**
 * Created by beijixiong on 2018/11/7.
 * 解密坚果VPN日志文件内容
 */

public class TestAESCryptActivity extends BaseActivity {


    private static final String key = "86712786e2205b50e80721462334364d";
    private Button btn;
    private TextView tv;
    //需要被解密文件地址
    private static final String sourcepath = MyApp.getApplication().getExternalFilesDir(null) + File.separator + "source.txt";
    //存放解密文件地址;
    private static final String targetpath = MyApp.getApplication().getExternalFilesDir(null) + File.separator + "clientlog" + File.separator + "target.txt";

    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decryptFile();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aescrypt;
    }

    /**
     * 解密文件
     */
    private void decryptFile() {
        if (FileUtils.isFileExists(sourcepath)) {
            list = FileUtils.readFile2List(sourcepath, "utf-8");
            if (list.size() > 0) {
                StringBuilder sbstr = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    String sourcecontent = list.get(i);
                    //解密内容
                    try {
                        String decryptcontent = AESCrypt.decrypt(key, sourcecontent);
                        sbstr.append(decryptcontent + "\r\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.e(e.getMessage());
                        return;
                    }
                }
                if (FileUtils.createOrExistsFile(targetpath)) {
                    writeDataToFile(targetpath, sbstr.toString(), false);
                }
            } else {
                ToastUtils.showLongToast("文件中没有数据，请查看");
                return;
            }
            tv.setText(targetpath);
        } else {
            ToastUtils.showLongToast("源文件不存在，请查看");
            return;
        }
    }

    /**
     * 将数据写入到文件中
     */
    public static boolean writeDataToFile(String filepath, String content, boolean append) {
        boolean writeflag = false;
        InputStream in = new ByteArrayInputStream(content.getBytes());
        if (FileUtils.writeFileFromIS(filepath, in, append)) {
            LogUtils.i("----->文件写入成功！");
            writeflag = true;
        } else {
            LogUtils.i("----->文件写入失败！");
            writeflag = false;
        }
        return writeflag;
    }
}
