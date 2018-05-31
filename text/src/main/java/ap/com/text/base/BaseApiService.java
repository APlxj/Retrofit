package ap.com.text.base;

import java.util.Map;

import ap.com.text.LoginEntry;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 类描述：retrofit Api
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public interface BaseApiService<T> {

    String Base_URL = "http://www.yingqianpos.com";
    String Base_IP = "/yunpos/vol/sync/";
    String IP = Base_IP + "/{url}/{url1}.form?";

    @GET(Base_IP + "/operator/ValidateOper.form?")
    Call<T> get(@Query("OperPw") String OperPw,
                @Query("HashCode") String HashCode,
                @Query("OperId") String OperId,
                @Query("LoginIP") String LoginIP,
                @Query("VersionCode") String VersionCode);

    @POST(Base_IP + "/operator/ValidateOper.form?")
    Call<T> executePost(@QueryMap Map<String, String> maps);


    public final String KEY = "data";

    @FormUrlEncoded
    @POST("/")
    public Call<String> doPost(@Field(KEY) String json);

    @POST(IP)
    Observable<T> post(@Path("url") String url,
                       @Path("url1") String url1,
                       @QueryMap Map<String, String> maps);

    @GET()
    Call<T> call(@Url String url,
                 @QueryMap Map<String, String> maps);

    @GET()
    Observable<LoginEntry> get(@Url String url,
                               @QueryMap Map<String, String> maps);

    @POST()
    Observable<LoginEntry> post(@Url String url,
                                @QueryMap Map<String, String> maps);


}
