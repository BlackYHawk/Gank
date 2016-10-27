package com.hawk.gank.lib.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hawk.gank.lib.BaseApplication;
import com.hawk.gank.lib.module.ActComponent;
import com.hawk.gank.lib.module.ActModule;
import com.hawk.gank.lib.ui.display.BaseDisplay;
import com.hawk.gank.lib.ui.presenter.BasePresenter;
import com.hawk.gank.lib.util.Preconditions;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity implements  {
	private ActComponent actComponent;
	private Unbinder unbinder;
	private BaseDisplay mDisplay;
	private BasePresenter mPreseneter;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(inflateContentView());

		initPresenter();
		initDisplay();
		Preconditions.checkState(mPreseneter != null, "Presenter not init");
		component().inject(mPreseneter);

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
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		mDisplay = null;
		super.onDestroy();
	}

	protected abstract int inflateContentView();

	protected abstract void initPresenter();

	protected abstract void initDisplay();

	public BasePresenter getPresenter() {
		return mPreseneter;
	}

	public BaseDisplay getDisplay() {
		return mDisplay;
	}

	@Override
	public final void setSupportActionBar(@Nullable Toolbar toolbar) {
		super.setSupportActionBar(toolbar);
	}

	public void setSupportActionBar(@Nullable Toolbar toolbar, boolean handleBackground) {
		setSupportActionBar(toolbar);
		getDisplay().setSupportActionBar(toolbar, handleBackground);
	}

	public BaseApplication getApplicationContext() {
		return (BaseApplication) getApplication();
	}

	public ActComponent component() {
		actComponent = getApplicationContext().component()
				.plus(new ActModule(this));

		return actComponent;
	}

	//  建议写在基类Activity里
	protected ActModule getActivityModule(){
		return new ActModule(this);
	}

}
