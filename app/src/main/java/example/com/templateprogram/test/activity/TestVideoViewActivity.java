package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.base.MyApp;
import example.com.templateprogram.http.ApiService;
import example.com.templateprogram.http.RetrofitServiceManager;
import example.com.templateprogram.test.view.CustomVideoView;
import example.com.templateprogram.utils.FileUtils;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.ToastUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by XQ on 2018/6/9.
 * 视频广告播放
 */

public class TestVideoViewActivity extends BaseActivity {


    private Activity mActivity;
    private CustomVideoView cv_videoview;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_videoview;
    }

    /**
     * 加载UI
     */
    public void initView() {
        mActivity = TestVideoViewActivity.this;
        cv_videoview = (CustomVideoView) findViewById(R.id.cv_videoview);

    }

    /**
     * 加载数据
     */
    public void initData() {
        uri = Uri.parse("android.resource://" + mActivity.getPackageName() + "/" + R.raw.test2);
        cv_videoview.playVideo(uri);
//        String url = "http://192.168.123.178/test1.mp4";
//        FileUtils.deleteFilesInDir(dirpath);
//        palyVideo(url);
    }

    /**
     * 下载视频文件
     */

    private String dirpath = MyApp.getApplication().getExternalFilesDir(null) + File.separator + "Video";

    public boolean downloadVideo(String url, final Context context) {
        final boolean[] downloadflag = {false};

        final String fileNametemporary = url.substring(url.lastIndexOf("/") + 1);
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlSync(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    downloadflag[0] = write2Data(response.body(), fileNametemporary, context);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.i("onFailure----->" + t.getMessage());
            }
        });
        return downloadflag[0];
    }

    /**
     * 把文件写入到手机中
     */
    public boolean write2Data(ResponseBody body, String fileName, Context context) {
        File file = new File(dirpath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File futureStudioIconFile = new File(dirpath + File.separator + fileName);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            byte[] fileReader = new byte[4096];
            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(futureStudioIconFile);
            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += read;
            }
            outputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("Exception----->" + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.e("Exception----->" + e.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.e("Exception----->" + e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * 播放视频
     */
    public void palyVideo(String url) {
        String fileNametemporary = url.substring(url.lastIndexOf("/") + 1);
        String filepath = dirpath + File.separator + fileNametemporary;
        if (FileUtils.isFileExists(filepath)) {
            uri = Uri.fromFile(new File(filepath));
            cv_videoview.playVideo(uri);
        } else {
            if (downloadVideo(url, mActivity)) {
                uri = Uri.fromFile(new File(filepath));
                cv_videoview.playVideo(uri);
            } else {
                ToastUtils.showLongToast("视频还没有准备好！");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cv_videoview != null) {
            cv_videoview.stopPlayback();
        }
    }
}
