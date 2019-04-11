package example.com.templateprogram.http;

import java.util.Map;

import example.com.templateprogram.entity.ApiResponse;
import example.com.templateprogram.entity.Member;
import example.com.templateprogram.utils.apiencrypt.LocationResponse;
import example.com.templateprogram.utils.apiencrypt.SigninResponseV2;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by XQ on 2017/6/20.
 * 客户端接口服务
 */
public interface ApiService {

    @GET("/TemplateProgramWeb/testByAction")
    Observable<ApiResponse<Member>> testByAction(@QueryMap Map<String, String> options);

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    /**
     * 判断客户端是否是在国内
     */
    @GET
    Observable<ApiResponse<LocationResponse>> location(
            @Url String fileUrl,
            @Header("fi") String fi,
            @Header("p1") String p1,
            @Header("p2") String p2,
            @Header("p3") String p3,
            @Header("uuid") String uuid);

    /**
     * 登录接口
     * 直接加密
     */
    @POST
    Observable<ApiResponse<SigninResponseV2>> signinv4(
            @Url String fileUrl,
            @HeaderMap Map<String, String> headers
    );

    /**
     * 登录接口
     * 在拦截器中修改值加密
     */
    @POST("/api/client/v4/signin")
    Observable<ApiResponse<SigninResponseV2>> signinv4_2(@QueryMap Map<String, Object> options);


}
