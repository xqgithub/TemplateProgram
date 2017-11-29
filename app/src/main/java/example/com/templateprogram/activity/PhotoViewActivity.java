package example.com.templateprogram.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.ToastUtils;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by admin on 2017/7/7.
 */
public class PhotoViewActivity extends BaseActivity {

    private PhotoView iv_photo1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        localImage();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photoview;
    }

    public void initView() {
        iv_photo1 = (PhotoView) findViewById(R.id.iv_photo1);


        iv_photo1.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                ToastUtils.showLongToast("图片被点击了！");
//                onBackPressed();
            }


        });
    }

    /**
     * 加载本地图片
     */
    private void localImage() {
//       加载本地图片，缩放处理
        try {
//       图片在asset目录中
            InputStream is = getAssets().open("huanci.jpg");
            Bitmap bm = BitmapFactory.decodeStream(is);
            iv_photo1.setImageBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
