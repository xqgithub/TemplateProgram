package example.com.templateprogram.http;

import java.util.Map;

import example.com.templateprogram.entity.ApiResponse;
import example.com.templateprogram.entity.Member;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
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
}
