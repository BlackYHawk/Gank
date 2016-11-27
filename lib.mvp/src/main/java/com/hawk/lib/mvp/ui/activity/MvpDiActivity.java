package com.hawk.lib.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;
import com.hawk.lib.mvp.ui.presenter.BasePresenterDelegate;
import com.hawk.lib.mvp.ui.presenter.GetPresenterDelegate;
import com.hawk.lib.mvp.ui.view.BaseView;
import com.hawk.lib.mvp.util.Preconditions;

public abstract class MvpDiActivity<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>,
		C extends BaseComponent<V, VC, P>> extends AppCompatActivity
		implements GetPresenterDelegate<V, VC, P> {

	protected BaseDisplay display;
	protected C component;
	protected P mPresenter;
	private BasePresenterDelegate<V, VC, P> mPresenterDelegate;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initializeDependence();
		initializDisplay();
		Preconditions.checkNotNull(component, "component not inited");
		mPresenter = component.presenter();
		mPresenterDelegate = new BasePresenterDelegate<V, VC, P>() {
			@Override
			protected P createPresenter() {
				return MvpDiActivity.this.createPresenter();
			}
		};
		mPresenterDelegate.onCreate(display);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPresenterDelegate.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mPresenterDelegate.onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mPresenterDelegate.onStop();
	}

	protected abstract void initializeDependence();

	protected void initializDisplay() {}

	@Override
	public BasePresenterDelegate<V, VC, P> getPresenterDelegate() {
		return mPresenterDelegate;
	}

	protected final P createPresenter() {
		return mPresenter;
	}

}
