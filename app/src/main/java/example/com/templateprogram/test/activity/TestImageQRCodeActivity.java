package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.InputStream;
import java.util.Hashtable;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.LogUtils;

/**
 * Created by beijixiong on 2018/12/12.
 * 1.生成二维码图片
 * 2.二维码图片合成到另一张图片上
 * 3.图片上添加文字
 */

public class TestImageQRCodeActivity extends BaseActivity {


    private Activity mActivity;
    private ImageView iv;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = TestImageQRCodeActivity.this;
        iv = findViewById(R.id.iv);
        btn = findViewById(R.id.btn);
        getViewTreeObserver(iv);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_imageqrcode;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
//                iv.setImageBitmap(createImageQRCode("https://www.hao123.com/"));
//                LogUtils.i("picWidth=-=" + picWidth);
//                LogUtils.i("picHeight=-=" + picHeight);
                iv.setImageBitmap(createImageText(mActivity, "我是小熊猫啦啦啦\r\n请扫码加我好友", "", R.drawable.beauty1));
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
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
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
     * 生成图片，添加文字
     */
    private int picWidth = 0;//生成图片的宽度
    private int picHeight = 0;//生成图片的高度

    public Bitmap createImageText(Context context,
                                  String content,
                                  String title,
                                  int DrawableId) {
        int titleTextSize = context.getResources().getDimensionPixelSize(R.dimen.deimen_18x);
        int contentTextSize = context.getResources().getDimensionPixelSize(R.dimen.deimen_18x);
        int content_top_offset = context.getResources().getDimensionPixelSize(R.dimen.deimen_35x);
        int QRCODE_SIZE = context.getResources().getDimensionPixelSize(R.dimen.deimen_100x);// 二维码尺寸

        int textColor = Color.BLACK;
        //最终生成的图片
        Bitmap result = Bitmap.createBitmap(picWidth, picHeight, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        Canvas canvas = new Canvas(result);
        //先画一整块白色矩形块
        canvas.drawRect(0, 0, picWidth, picHeight, paint);

        /**
         * 绘制的底图
         */
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        InputStream is = context.getResources().openRawResource(DrawableId);
        Bitmap bitmap_bg = BitmapFactory.decodeStream(is, null, options);
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
        canvas.drawBitmap(bitmap_qrcode, picWidth - QRCODE_SIZE - 100, picHeight - QRCODE_SIZE - 100, paint);
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
}
