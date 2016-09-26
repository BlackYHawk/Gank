package com.hawk.gank.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.hawk.gank.AppContext;
import com.hawk.gank.R;
import com.hawk.gank.http.LeanCloudIO;
import com.hawk.gank.interfaces.Logger;
import com.hawk.gank.modules.ActComponent;
import com.hawk.gank.modules.ActModule;
import com.hawk.gank.ui.fragment.base.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseActivity extends AppCompatActivity {
	// 当有Fragment Attach到这个Activity的时候，就会保存
	private Map<String, WeakReference<BaseFragment>> fragmentRefs;
	private ActComponent actComponent;
	private Unbinder unbinder;
	protected @Inject Logger logger;
	protected @Inject LeanCloudIO leanCloudIO;
	protected @Inject CompositeSubscription mSubscription;
	protected @Nullable @BindView(R.id.toolbar) Toolbar mToolbar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		component().inject(this);

		fragmentRefs = new ArrayMap<String, WeakReference<BaseFragment>>();
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		handleIntent(intent);
	}

	protected void handleIntent(Intent intent) {
	}

	@Override
	public void setContentView(int layoutResID) {
		setContentView(View.inflate(this, layoutResID, null));
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		unbinder = ButterKnife.bind(this, view);

		if (mToolbar != null) {
			setSupportActionBar(mToolbar);
		}
	}

	protected void setDisplayBack() {
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(false);
		}
	}

	public void setDisplayTitle(int titleRes) {
		if(getSupportActionBar() != null) {
			getSupportActionBar().setSubtitle(null);
			getSupportActionBar().setTitle(titleRes);
		}
	}

	public void addFragment(String tag, BaseFragment fragment) {
		if(fragmentRefs == null || fragmentRefs.containsKey(tag)) {
			return;
		}
		fragmentRefs.put(tag, new WeakReference<BaseFragment>(fragment));
	}

	public void removeFragment(String tag) {
		if(fragmentRefs == null || !fragmentRefs.containsKey(tag)) {
			return;
		}
		fragmentRefs.remove(tag);
	}

	public void backFragment() {
		getSupportFragmentManager().popBackStack();
	}

	protected void addSubscription(Subscription subscription) {
		mSubscription.add(subscription);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (onBackClick()) {
				finish();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean onBackClick() {
		Set<String> keys = fragmentRefs.keySet();
		for (String key : keys) {
			WeakReference<BaseFragment> fragmentRef = fragmentRefs.get(key);
			BaseFragment fragment = fragmentRef.get();
			if (fragment != null && fragment.isVisible() && fragment.onBackClick())
				return true;
		}

		return false;
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		if (this.mSubscription != null) {
			this.mSubscription.unsubscribe();
		}
		super.onDestroy();
	}

	public AppContext getAppContext() {
		return AppContext.getInstance();
	}

	public ActComponent component() {
		actComponent = getAppContext().component()
				.plus(new ActModule(this));

		return actComponent;
	}

	//  建议写在基类Activity里
	protected ActModule getActivityModule(){
		return new ActModule(this);
	}

}
