package example.com.templateprogram.test.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.security.KeyChain;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.Enumeration;

import butterknife.BindView;
import butterknife.OnClick;
import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.LogUtils;

/**
 * Created by Administrator on 2019/11/13.
 * 安装证书
 */

public class TestInstallCertificateActivity extends BaseActivity {


    @BindView(R.id.tv2)
    TextView tv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        boolean isInstalled = isInstalledCertificates("xroute");
        if (isInstalled) {
            tv2.setText("true");
        } else {
            tv2.setText("false");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_installcertificate;
    }


    /**
     * 点击事件
     */
    @OnClick(R.id.btn1)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                installCert(TestInstallCertificateActivity.this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 判断是否安装了指定CA证书
     */
    private boolean isInstalledCertificates(String caName) {
        try {
            KeyStore ks = KeyStore.getInstance("AndroidCAStore");
            if (ks != null) {
                ks.load(null, null);
                Enumeration<String> aliases = ks.aliases();
                while (aliases.hasMoreElements()) {
                    String alias = (String) aliases.nextElement();
                    java.security.cert.X509Certificate cert = (java.security.cert.X509Certificate) ks.getCertificate(alias);
                    if (cert.getIssuerDN().getName().contains(caName)) {
                        return true;
                    }
                    //打印所有证书的名字
//                    LogUtils.i("CAname =-= " + cert.getIssuerDN().getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
        return false;
    }

    /**
     * 安装CA证书
     */
    private void installCert(Context context) {
        InputStream assetsIn = null;
        Intent intent = KeyChain.createInstallIntent();
        try {
            //1.获取证书流，注意参数为assets目录文件全名
            assetsIn = context.getAssets().open("server.pem");
            byte[] cert = new byte[10240];
            assetsIn.read(cert);
            javax.security.cert.X509Certificate x509 = null;
            x509 = javax.security.cert.X509Certificate.getInstance(cert);
            //2.将证书传给系统
            intent.putExtra(KeyChain.EXTRA_CERTIFICATE, x509.getEncoded());
            //3.此处为给证书设置默认别名，第二个参数可自定义，设置后无需用户输入
            intent.putExtra("name", "lufei");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
    }


}
