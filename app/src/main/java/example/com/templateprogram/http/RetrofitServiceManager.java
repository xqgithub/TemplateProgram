package example.com.templateprogram.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import example.com.templateprogram.constants.ConfigConstants;
import example.com.templateprogram.test.myclass.PublicPracticalMethod;
import example.com.templateprogram.utils.LogUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2017/6/20.
 */
public class RetrofitServiceManager {
    public static final boolean isDebug = true;
    private static final int DEFAULT_CONNECT_TIME_OUT = 10; // 超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private OkHttpClient.Builder httpClientBuilder;

    private OkHttpClient sClient;

    private RetrofitServiceManager() {
        httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        httpClientBuilder.connectTimeout(DEFAULT_CONNECT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        httpClientBuilder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        if (RetrofitServiceManager.isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (message.startsWith("<--") || message.startsWith("{")) {
                        logConversionPrint(message);
                    }
                }
            });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging)
                    .addNetworkInterceptor(new StethoInterceptor());
        }
        //添加自定义拦截器
        httpClientBuilder.addInterceptor(new OkHttpInterceptor());
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return
     */
    public static RetrofitServiceManager getInstance() {
//        return SingletonHolder.INSTANCE;
        return new RetrofitServiceManager();
    }

    /**
     * 获取对应的ApiService
     *
     * @return
     */
    public ApiService getApiService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();


        return new Retrofit.Builder()
                .baseUrl(ConfigConstants.base_url)
//                .client(httpClientBuilder.build())
                .client(lgnoreHttps())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiService.class);
    }

    /**
     * 忽略https验证
     */
    public OkHttpClient lgnoreHttps() {
        sClient = httpClientBuilder.build();
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HostnameVerifier hv1 = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        String workerClassName = "okhttp3.OkHttpClient";

        try {
            Class workerClass = Class.forName(workerClassName);
            Field hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier");
            hostnameVerifier.setAccessible(true);
            hostnameVerifier.set(sClient, hv1);

            Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
            sslSocketFactory.setAccessible(true);
            sslSocketFactory.set(sClient, sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sClient;
    }

    /**
     * OkHttp拦截器
     */
    private class OkHttpInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取request
            Request request = chain.request();
            return chain.proceed(PublicPracticalMethod.getInstance().OkHttpInterceptorAPIEncrypt(request));
        }
    }


    /**
     * 日志打印转换类
     */
    private void logConversionPrint(String message) {
        if (message.startsWith("{")) {
            LogUtils.json(message);
        } else {
            LogUtils.i(message);
        }
    }


}
