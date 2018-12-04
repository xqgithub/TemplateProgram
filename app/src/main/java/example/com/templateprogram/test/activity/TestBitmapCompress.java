package example.com.templateprogram.test.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.base.MyApp;
import example.com.templateprogram.utils.LogUtils;

/**
 * Created by beijixiong on 2018/12/3.
 * BitMap的压缩方法
 */

public class TestBitmapCompress extends BaseActivity {

    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv = findViewById(R.id.iv);
//        qualityCompress(MyApp.getApplication(), R.mipmap.yindao3);
//        samplingRateCompress(MyApp.getApplication(), R.mipmap.yindao3);
//        martixCompress(MyApp.getApplication(), R.mipmap.yindao3);
//        RGB565(MyApp.getApplication(), R.mipmap.yindao3);
        iv.setImageBitmap(samplingRateCompress(MyApp.getApplication(), R.mipmap.yindao3));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_bitmapcompress;
    }

    /**
     * 00001
     * 质量压缩
     * 图片的大小是没有变的，因为质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度等，来达到压缩图片的目的，这也是为什么该方法叫质量压缩方法
     * 图片的长，宽，像素都不变，那么bitmap所占内存大小是不会变
     * 看到bytes.length是随着quality变小而变小的。这样适合去传递二进制的图片数据，比如微信分享图片，要传入二进制数据过去，限制32kb之内
     * png格式，quality就没有作用了，bytes.length不会变化，因为png图片是无损的，不能进行压缩
     */
    private void qualityCompress(Context context, int DrawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), DrawableId);
        LogUtils.i("压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
        for (int i = 10; i < 100; i = i + 10) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, i, baos);
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream isBm = new ByteArrayInputStream(bytes);// 把压缩后的数据baos存放到ByteArrayInputStream中
            bitmap = BitmapFactory.decodeStream(isBm, null, null);
            LogUtils.i("压缩后图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                    + "M 宽度为" + bitmap.getWidth() + " 高度为" + bitmap.getHeight()
                    + " bytes.length=" + (bytes.length / 1024) + "KB"
                    + " quality=" + i);
        }
    }

    /**
     * 00002
     * 采样率压缩
     * 置inSampleSize的值(int类型)后，假如设为2，则宽和高都为原来的1/2，宽高都减少了，自然内存也降低了
     * 代码没用过options.inJustDecodeBounds = true; 因为我是固定来取样的数据，为什么这个压缩方法叫采样率压缩，是因为配合inJustDecodeBounds，先获取图片的宽、高【这个过程就是取样】，然后通过获取的宽高，动态的设置inSampleSize的值
     * inJustDecodeBounds设置为true的时候，BitmapFactory通过decodeResource或者decodeFile解码图片时，将会返回空(null)的Bitmap对象，这样可以避免Bitmap的内存分配，但是它可以返回Bitmap的宽度、高度以及MimeType
     */
    private Bitmap samplingRateCompress(Context context, int DrawableId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream is = context.getResources().openRawResource(DrawableId);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
//        LogUtils.i("压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
//                + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
        options.inSampleSize = 1;
        options.inJustDecodeBounds = false;
        Bitmap bitmap1 = BitmapFactory.decodeStream(context.getResources().openRawResource(DrawableId), null, options);

        LogUtils.i("压缩后图片的大小" + (bitmap1.getByteCount() / 1024 / 1024)
                + "M 宽度为" + bitmap1.getWidth() + " 高度为" + bitmap1.getHeight());
        return bitmap1;
    }

    /**
     * 00002
     * 缩放压缩
     * bitmap的长度和宽度分别缩小了一半，图片大小缩小了四分之一
     */
    private Bitmap martixCompress(Context context, int DrawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), DrawableId);
        LogUtils.i("压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        LogUtils.i("压缩后图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                + "M 宽度为" + bitmap.getWidth() + " 高度为" + bitmap.getHeight());
        return bitmap;
    }

    /**
     * 00002
     * RGB_565法
     */
    private void RGB565(Context context, int DrawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), DrawableId);
        LogUtils.i("压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
        InputStream is = context.getResources().openRawResource(DrawableId);
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeStream(is, null, options2);
        LogUtils.i("压缩后图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                + "M 宽度为" + bitmap.getWidth() + " 高度为" + bitmap.getHeight());
    }


}
