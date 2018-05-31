package ap.com.text.base;

import android.app.Activity;
import android.os.Bundle;

import ap.com.text.LoginEntry;
import butterknife.ButterKnife;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public abstract class BaseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    public static BaseApiService httpApi() {
        return BaseApiAdapter.getInstance().create(BaseApiService.class);
    }

    public abstract int getLayout();
}
