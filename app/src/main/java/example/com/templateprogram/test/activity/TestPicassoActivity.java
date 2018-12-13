package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.PicassoUtils;

/**
 * Created by XQ on 2018/2/2.
 */
public class TestPicassoActivity extends BaseActivity implements View.OnClickListener {


    private static String url = "http://192.168.123.178:8080/two.jpg";
    private ImageView iv_picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv_picasso = (ImageView) findViewById(R.id.iv_picasso);
        PicassoUtils.initPicasso(TestPicassoActivity.this);
//        PicassoUtils.loadPicFromUrl("https://upload-images.jianshu.io/upload_images/3134797-927971adae25267e.jpg", iv_picasso);
//        PicassoUtils.loadPicFromFile(url, iv_picasso);
//        PicassoUtils.fetchPic("https://upload-images.jianshu.io/upload_images/3134797-927971adae25267e.jpg");
        PicassoUtils.loadResPic(R.drawable.beauty1, iv_picasso);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_picasso;
    }

    @Override
    public void onClick(View v) {

    }
}
