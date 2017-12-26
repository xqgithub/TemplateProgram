package example.com.templateprogram.http;

import java.util.Map;

import example.com.templateprogram.entity.ApiResponse;
import example.com.templateprogram.entity.Member;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by XQ on 2017/6/20.
 * 客户端接口服务
 */
public interface ApiService {

    @GET("/TemplateProgramWeb/testByAction")
    Observable<ApiResponse<Member>> testByAction(@QueryMap Map<String, String> options);
}
