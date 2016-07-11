package com.hawk.gank.ui.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.hawk.gank.R;

import java.lang.reflect.Method;

/**
 * Created by lan on 2016/7/6.
 */
public class FragmentContainerActivity extends BaseActivity {
    public static final String FRAGMENT_TAG = "FRAGMENT_CONTAINER";

    /**
     * 启动一个界面
     * @param activity
     * @param clazz
     * @param args
     */
    public static void launch(Activity activity, Class<? extends Fragment> clazz, Bundle args) {
        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null)
            intent.putExtra("args", args);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String className = getIntent().getStringExtra("className");
        if (TextUtils.isEmpty(className)) {
            finish();
            return;
        }

        int contentId = R.layout.ac_ui_fragment_container;
        Bundle values = (Bundle) getIntent().getBundleExtra("args");

        Fragment fragment = null;
        if (savedInstanceState == null) {
            try {
                Class clazz = Class.forName(className);
                fragment = (Fragment) clazz.newInstance();
                // 设置参数给Fragment
                if (values != null) {
                    try {
                        Method method = clazz.getMethod("setArguments", new Class[] { Bundle.class });
                        method.invoke(fragment, values);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                finish();
                return;
            }
        }

        setContentView(contentId);

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}

