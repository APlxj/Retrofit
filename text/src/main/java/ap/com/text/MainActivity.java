package ap.com.text;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import ap.com.text.base.BaseActivity;
import ap.com.text.base.BaseApiService;
import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.tv)
    public void getLoginInformation() {
    }

    @OnClick(R.id.tv1)
    public void executeGet() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = new HashMap<>();
                    map.put("OperPw", "f6634250652bcb4f");
                    map.put("HashCode", "bed1b1ff9e7d50948941316116a825c3d9f6f9de109feb78bc0fe2c84becf4077fa4e3c08b6295ee");
                    map.put("OperId", "18300070007");
                    map.put("LoginIP", "10.0.4.15");
                    map.put("VersionCode", "R1.4.3");
                    final Response<LoginEntry> response = httpApi().executePost(map).execute()
                    /*.enqueue(new Callback<LoginEntry>() {
                        @Override
                        public void onResponse(Call<LoginEntry> call, Response<LoginEntry> response) {
                            tv1.setText(response.body().toString());
                        }

                        @Override
                        public void onFailure(Call<LoginEntry> call, Throwable t) {

                        }
                    })*/;
                    if (response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv1.setText(response.body().toString());
                            }
                        });
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @OnClick(R.id.tv2)
    public void rxPost() {
        Map<String, String> map = new HashMap<>();
        map.put("OperPw", "f6634250652bcb4f");
        map.put("HashCode", "bed1b1ff9e7d50948941316116a825c3d9f6f9de109feb78bc0fe2c84becf4077fa4e3c08b6295ee");
        map.put("OperId", "18300070007");
        map.put("LoginIP", "10.0.4.15");
        map.put("VersionCode", "R1.4.3");
        try {
            httpApi().post("operator", "ValidateOper", map)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Observer<LoginEntry>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            tv2.setText(e.toString());
                        }

                        @Override
                        public void onNext(LoginEntry LoginEntry) {
                            tv2.setText(LoginEntry.toString());
                        }
                    });
        } catch (Throwable e) {
        }
    }

    @OnClick(R.id.tv3)
    public void postUrl() {
        Map<String, String> map = new HashMap<>();
        map.put("OperPw", "f6634250652bcb4f");
        map.put("HashCode", "bed1b1ff9e7d50948941316116a825c3d9f6f9de109feb78bc0fe2c84becf4077fa4e3c08b6295ee");
        map.put("OperId", "18300070007");
        map.put("LoginIP", "10.0.4.15");
        map.put("VersionCode", "R1.4.3");
        httpApi().post(BaseApiService.Base_IP + "/operator/ValidateOper.form?", map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<LoginEntry>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginEntry o) {
                        tv3.setText(o.toString());
                    }
                });
    }
}
