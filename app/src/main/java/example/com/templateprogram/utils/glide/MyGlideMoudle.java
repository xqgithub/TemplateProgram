package example.com.templateprogram.utils.glide;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

import example.com.templateprogram.base.MyApp;

/**
 * Created by beijixiong on 2019/3/1.
 */
public class MyGlideMoudle implements GlideModule {
    //文件缓存的大小
    public static int diskCacheSizeBytes = 1024 * 1024 * 10; // 100 MB
    //缓存目录
    public static String diskCacheDir = MyApp.getApplication().getExternalFilesDir(null) + File.separator +
            "TemplateProgram" + File.separator +
            "MyGlide";


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskLruCacheFactory(diskCacheDir, diskCacheSizeBytes));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
