package com.hawk.lib.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.component.GetComponent;
import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;

public abstract class MvpDiActivity<V extends BaseDisplay, P extends BasePresenter<V>, C extends BaseComponent<V, P>>
		extends AppCompatActivity implements GetComponent<C> {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initializeDependence();
		super.onCreate(savedInstanceState);
	}

	protected abstract void initializeDependence();

}
