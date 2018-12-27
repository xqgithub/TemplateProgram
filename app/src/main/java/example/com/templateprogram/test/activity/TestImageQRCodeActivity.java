package example.com.templateprogram.test.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import example.com.templateprogram.R;
import example.com.templateprogram.activity.PermissionsActivity;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.base.MyApp;
import example.com.templateprogram.utils.FileUtils;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.PermissionsChecker;
import example.com.templateprogram.utils.PicassoUtils;
import example.com.templateprogram.utils.ToastUtils;

/**
 * Created by beijixiong on 2018/12/12.
 * 1.生成二维码图片
 * 2.二维码图片合成到另一张图片上
 * 3.图片上添加文字
 */

public class TestImageQRCodeActivity extends BaseActivity {


    private Activity mActivity;
    private ImageView iv;
    private String picurl = "http://d.5857.com/cy_170929/003.jpg";
    private static final int REQUEST_CODE = 0; // 请求码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = TestImageQRCodeActivity.this;
        iv = findViewById(R.id.iv);
        getViewTreeObserver(iv);
        //加载网络图片
        PicassoUtils.initPicasso(mActivity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_imageqrcode;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
//                LogUtils.i("picWidth=-=" + picWidth);
//                LogUtils.i("picHeight=-=" + picHeight);

                PermissionsChecker mPermissionsChecker = new PermissionsChecker(this);
                if (mPermissionsChecker.lacksPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    PermissionsActivity.startActivityForResult(mActivity,
                            REQUEST_CODE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
//                    ToastUtils.showLongToastSafe("权限通过了，谢谢配合！");
                    createImageTextFromPicCache(
                            mActivity,
                            "我是路飞，会成为海贼王的男人\n请扫码加我好友",
                            R.drawable.beauty1,
                            picurl
                    );
                }
                break;
            case R.id.btn2:
                PicassoUtils.fetchPic(picurl);
                break;
        }
    }


    private static final String FORMAT_NAME = "JPG";

    /**
     * @param content 二维码内容
     * @return 返回二维码图片
     */
    private static final String CHARSET = "UTF-8";


    public Bitmap createImageQRCode(Context context, String content, int QRCODE_SIZE) {
        //配置参数
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        //容错级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置空白边距的宽度
        hints.put(EncodeHintType.MARGIN, 2);
        BitMatrix bitMatrix = null;
        Bitmap image = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(y, x)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                    //                    image.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
                image = Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return image;
    }

    /**
     * 二维码图片中添加logo
     */

    public Bitmap addLogo(Bitmap bitmap_qrcode, Bitmap bitmap_logo) {
        if (bitmap_qrcode == null) {
            return null;
        }
        if (bitmap_logo == null) {
            return null;
        }
        //获取图片的宽高
        int bitmap_qrcode_Width = bitmap_qrcode.getWidth();
        int bitmap_qrcode_Height = bitmap_qrcode.getHeight();
        int bitmap_logo_Width = bitmap_logo.getWidth();
        int bitmap_logo_Height = bitmap_logo.getHeight();
        //logo大小为二维码整体大小的1/5
        float scaleFactor = bitmap_qrcode_Width * 1.0f / 5 / bitmap_logo_Width;
        Bitmap bitmap = Bitmap.createBitmap(bitmap_qrcode_Width, bitmap_qrcode_Height, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(bitmap_qrcode, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, bitmap_qrcode_Width / 2, bitmap_qrcode_Height / 2);
            canvas.drawBitmap(bitmap_logo, (bitmap_qrcode_Width - bitmap_logo_Width) / 2,
                    (bitmap_qrcode_Height - bitmap_logo_Height) / 2, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
            bitmap = null;
        }
        return bitmap;
    }


    /**
     * 生成图片，添加文字
     */
    private int picWidth = 0;//生成图片的宽度
    private int picHeight = 0;//生成图片的高度

    public Bitmap createImageText(Context context,
                                  String content,
                                  int DrawableId, Bitmap bitmap_bg) {
        int titleTextSize = context.getResources().getDimensionPixelSize(R.dimen.deimen_18x);
        int contentTextSize = context.getResources().getDimensionPixelSize(R.dimen.deimen_18x);
        int content_top_offset = context.getResources().getDimensionPixelSize(R.dimen.deimen_35x);
        int QRCODE_SIZE = context.getResources().getDimensionPixelSize(R.dimen.deimen_100x);// 二维码尺寸
        int qrcode_x_offset = context.getResources().getDimensionPixelSize(R.dimen.deimen_30x);// 二维码尺寸
        int qrcode_y_offset = context.getResources().getDimensionPixelSize(R.dimen.deimen_30x);// 二维码尺寸

        int textColor = Color.RED;
        //最终生成的图片
        Bitmap result = Bitmap.createBitmap(picWidth, picHeight, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        Canvas canvas = new Canvas(result);
        //先画一整块矩形块
        canvas.drawRect(0, 0, picWidth, picHeight, paint);

        /**
         * 绘制的底图
         */
        if (bitmap_bg == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            InputStream is = context.getResources().openRawResource(DrawableId);
            bitmap_bg = BitmapFactory.decodeStream(is, null, options);
        }
        // 获得图片的宽高
        int bitmap_bg_width = bitmap_bg.getWidth();
        int bitmap_bg_height = bitmap_bg.getHeight();
        //想要的新宽和高
        int bitmap_bg_new_width = picWidth;
        int bitmap_bg_new_height = picHeight;
        // 计算缩放比例
        float scaleWidth = ((float) bitmap_bg_new_width) / bitmap_bg_width;
        float scaleHeight = ((float) bitmap_bg_new_height) / bitmap_bg_height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap bitmap_new_bg = Bitmap.createBitmap(bitmap_bg, 0, 0, bitmap_bg_width, bitmap_bg_height, matrix,
                true);
        canvas.drawBitmap(bitmap_new_bg,
                0,
                0, paint);

        /**
         * 画title文字
         */
//        Rect bounds = new Rect();
//        paint.setColor(textColor);
//        paint.setTextSize(contentTextSize);
//        //获取文字的字宽高，以便将文字与图片中心对齐
//        paint.getTextBounds(content, 0, content.length(), bounds);
//        int content_x = (picWidth - bounds.width()) / 2;
//        int content_y = bounds.height() + content_top_offset;
//        canvas.drawText(content,
//                content_x,
//                content_y, paint);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(contentTextSize);
        textPaint.setAntiAlias(true);
        StaticLayout layout = new StaticLayout(content, textPaint, picWidth, Layout.Alignment.ALIGN_CENTER, 1.5F, 0.0F, true);
        // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
        canvas.save();
        canvas.translate(0, content_top_offset);
        layout.draw(canvas);
        canvas.restore();//别忘了restore

        /**
         * 二维码图片
         */
        Bitmap bitmap_qrcode = createImageQRCode(context,
                "https://www.youtube.com/",
                QRCODE_SIZE);
        //二维码图片添加logo
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inJustDecodeBounds = false;
        @SuppressLint("ResourceType")
        InputStream is2 = context.getResources().openRawResource(R.mipmap.jianguo_logo);
        Bitmap bitmap_logo = BitmapFactory.decodeStream(is2, null, options2);
        Bitmap bitmap_qrcode_logo = addLogo(bitmap_qrcode, bitmap_logo);
        if (bitmap_qrcode_logo != null) {
            canvas.drawBitmap(bitmap_qrcode_logo,
                    picWidth - QRCODE_SIZE - qrcode_x_offset,
                    picHeight - QRCODE_SIZE - qrcode_y_offset,
                    paint);
        }
        return result;
    }

    /**
     * 获取控件的宽度和高度
     */
    public void getViewTreeObserver(final View view) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                picWidth = view.getWidth();
                picHeight = view.getHeight();
            }
        });
    }

    /**
     * 获取本地图片缓存，再生成图片
     */
    public void createImageTextFromPicCache(final Context context,
                                            final String content,
                                            final int DrawableId,
                                            String url) {
        PicassoUtils.mpicasso.load(url)
                .config(Bitmap.Config.RGB_565)
                .networkPolicy(NetworkPolicy.OFFLINE)//加载图片的时候只从本地读取，除非网络正常且本地找不到资源的情况下
                .error(R.mipmap.ic_launcher) //下载出错显示的图片
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        iv.setImageBitmap(createImageText(context, content, DrawableId, bitmap));
                        saveBitMapToSDCard(createImageText(context, content, DrawableId, bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        iv.setImageBitmap(createImageText(context, content, DrawableId, null));
                        saveBitMapToSDCard(createImageText(context, content, DrawableId, null));
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    /**
     * 将合成的图片保存在本地
     */
    private static String picdir = MyApp.getApplication().getExternalFilesDir(null) +
            File.separator + "TemplateProgram" +
            File.separator + "sharepictrue";

    public void saveBitMapToSDCard(Bitmap bitmap) {
//        String picfile_path = picdir + File.separator + "haha.jpg";
        String picfile_path = getSystemPhotoPath() + File.separator + "haha.jpg";
        FileOutputStream fos = null;
        //图片是否保存成功标识
        boolean saveflag = false;
        try {
            if (FileUtils.createOrExistsFile(picfile_path)) {
                fos = new FileOutputStream(picfile_path);
                if (fos != null) {
                    saveflag = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                }
                File pic_file = new File(picfile_path);
                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(pic_file);
                mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.e(e.getMessage());
                }
            }
        }
    }

    /**
     * 获取手机本地相册地址
     */
    private String getSystemPhotoPath() {
        String pathSaveParent = "";
        try {
            pathSaveParent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .getAbsolutePath();
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return pathSaveParent;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            ToastUtils.showLongToastSafe("你为什么要拒绝我呢？fuck");
        }
    }
}
