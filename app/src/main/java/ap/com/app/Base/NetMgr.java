//package ap.com.app.Base;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.CookieJar;
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * 类描述：提出Retrofits实现类，提供设置超时时间、添加拦截等处理的接口
// * 创建人：swallow.li
// * 创建时间：
// * Email: swallow.li@kemai.cn
// * 修改备注：
// */
//public class NetMgr {
//    private final long connectTimeoutMills = 10 * 1000L;
//    private final long readTimeoutMills = 10 * 1000L;
//    private NetProvider sProvider = null;
//    private static NetMgr instance;
//    private Map<String, NetProvider> providerMap = new HashMap<>();
//    private Map<String, Retrofit> retrofitMap = new HashMap<>();
//    private Map<String, OkHttpClient> clientMap = new HashMap<>();
//
//
//    public static NetMgr getInstance() {
//        if (instance == null) {
//            synchronized (NetMgr.class) {
//                if (instance == null) {
//                    instance = new NetMgr();
//                }
//            }
//        }
//        return instance;
//    }
//
//
//    public <S> S get(String baseUrl, Class<S> service) {
//        return getInstance().getRetrofit(baseUrl).create(service);
//    }
//
//    public void registerProvider(NetProvider provider) {
//        this.sProvider = provider;
//    }
//
//    public void registerProvider(String baseUrl, NetProvider provider) {
//        getInstance().providerMap.put(baseUrl, provider);
//    }
//
//    public NetProvider getCommonProvider() {
//        return sProvider;
//    }
//
//    public void clearCache() {
//        getInstance().retrofitMap.clear();
//        getInstance().clientMap.clear();
//    }
//
//    public Retrofit getRetrofit(String baseUrl) {
//        return getRetrofit(baseUrl, null);
//    }
//
//    public Retrofit getRetrofit(String baseUrl, NetProvider provider) {
//        if (empty(baseUrl)) {
//            throw new IllegalStateException("baseUrl can not be null");
//        }
//        if (retrofitMap.get(baseUrl) != null) {
//            return retrofitMap.get(baseUrl);
//        }
//
//        if (provider == null) {
//            provider = providerMap.get(baseUrl);
//            if (provider == null) {
//                provider = sProvider;
//            }
//        }
//        checkProvider(provider);
//
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .create();
//
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .client(getClient(baseUrl, provider))
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson));
//
//        Retrofit retrofit = builder.build();
//        retrofitMap.put(baseUrl, retrofit);
//        providerMap.put(baseUrl, provider);
//
//        return retrofit;
//    }
//
//    private boolean empty(String baseUrl) {
//        return baseUrl == null || baseUrl.isEmpty();
//    }
//
//    private OkHttpClient getClient(String baseUrl, NetProvider provider) {
//        if (empty(baseUrl)) {
//            throw new IllegalStateException("baseUrl can not be null");
//        }
//        if (clientMap.get(baseUrl) != null) {
//            return clientMap.get(baseUrl);
//        }
//
//        checkProvider(provider);
//
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        builder.connectTimeout(provider.configConnectTimeoutSecs() != 0
//                ? provider.configConnectTimeoutSecs()
//                : connectTimeoutMills, TimeUnit.SECONDS);
//        builder.readTimeout(provider.configReadTimeoutSecs() != 0
//                ? provider.configReadTimeoutSecs() : readTimeoutMills, TimeUnit.SECONDS);
//
//        builder.writeTimeout(provider.configWriteTimeoutSecs() != 0
//                ? provider.configReadTimeoutSecs() : readTimeoutMills, TimeUnit.SECONDS);
//        CookieJar cookieJar = provider.configCookie();
//        if (cookieJar != null) {
//            builder.cookieJar(cookieJar);
//        }
//        provider.configHttps(builder);
//
//        RequestHandler handler = provider.configHandler();
//        if (handler != null) {
//            builder.addInterceptor(new NetInterceptor(handler));
//        }
//
//        Interceptor[] interceptors = provider.configInterceptors();
//        if (!empty(interceptors)) {
//            for (Interceptor interceptor : interceptors) {
//                builder.addInterceptor(interceptor);
//            }
//        }
//
//        if (provider.configLogEnable()) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addInterceptor(loggingInterceptor);
//        }
//
//        OkHttpClient client = builder.build();
//        clientMap.put(baseUrl, client);
//        providerMap.put(baseUrl, provider);
//
//        return client;
//    }
//
//    private boolean empty(Interceptor[] interceptors) {
//        return interceptors == null || interceptors.length == 0;
//    }
//
//    private void checkProvider(NetProvider provider) {
//        if (provider == null) {
//            throw new IllegalStateException("must register provider first");
//        }
//    }
//
//    public Map<String, Retrofit> getRetrofitMap() {
//        return retrofitMap;
//    }
//
//    public Map<String, OkHttpClient> getClientMap() {
//        return clientMap;
//    }
//}
