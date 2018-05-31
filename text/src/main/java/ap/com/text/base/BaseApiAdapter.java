package ap.com.text.base;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit的实体类
 */
public class BaseApiAdapter {

    private static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            synchronized (BaseApiAdapter.class) {
                if (retrofit == null) {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    OkHttpClient.Builder builder = okHttpClient.newBuilder();
                    builder.retryOnConnectionFailure(true);
                    retrofit = new Retrofit.Builder()
                            .client(okHttpClient)
                            .baseUrl(BaseApiService.Base_URL)
                            //增加返回值为Gson的支持(以实体类返回)
                            .addConverterFactory(GsonConverterFactory.create())
                            //增加返回值为Oservable<T>的支持
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
