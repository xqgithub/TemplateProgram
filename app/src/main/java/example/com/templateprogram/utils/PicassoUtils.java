package example.com.templateprogram.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import example.com.templateprogram.R;
import example.com.templateprogram.base.MyApp;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by XQ on 2018/2/5.
 * Picasso工具类
 */
public class PicassoUtils {


    public static Picasso mpicasso;

    //开屏广告图片缓存路径
    private static String dir = MyApp.getApplication().getFilesDir() + File.separator + "SAcceleratorOpenAd";

    /**
     * 初始化Picasso
     */
    public static Picasso initPicasso(Context mcontext) {

        if (mpicasso == null) {
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            long maxSize = Runtime.getRuntime().maxMemory() / 8;//设置图片缓存大小为运行时缓存的八分之一
            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(new Cache(file, maxSize))
                    .build();

            mpicasso = new Picasso.Builder(mcontext)
                    .downloader(new OkHttp3Downloader(client))
                    .build();
            return mpicasso;
        } else {
            return mpicasso;
        }
    }

    /**
     * 从网络上面下载图片文件
     */
    public static void loadPicFromUrl(String url, ImageView iv_picasso) {
        mpicasso.load(url)
                .config(Bitmap.Config.RGB_565)
//                .placeholder(R.mipmap.ic_launcher)  //设置占位图,下载图片时显示的
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .error(R.mipmap.ic_launcher) //下载出错显示的图片
                .fit().into(iv_picasso, new Callback() {
            @Override
            public void onSuccess() {
                LogUtils.i("loadPicFromUrl----->图片加载成功");
            }

            @Override
            public void onError() {
                LogUtils.i("loadPicFromUrl----->图片加载失败");
            }
        });
    }

    /**
     * 从本地缓存读取文件
     */
    public static void loadPicFromFile(String url, ImageView iv_picasso) {
        mpicasso.load(url)
                .config(Bitmap.Config.RGB_565)
//                .placeholder(R.mipmap.ic_launcher)  //设置占位图,下载图片时显示的
                .networkPolicy(NetworkPolicy.OFFLINE)//加载图片的时候只从本地读取，除非网络正常且本地找不到资源的情况下
                .error(R.mipmap.ic_launcher) //下载出错显示的图片
                .fit().into(iv_picasso, new Callback() {
            @Override
            public void onSuccess() {
                LogUtils.i("loadPicFromFile----->图片加载成功");
            }

            @Override
            public void onError() {
                LogUtils.i("loadPicFromFile----->图片加载失败");
            }
        });
    }


}
