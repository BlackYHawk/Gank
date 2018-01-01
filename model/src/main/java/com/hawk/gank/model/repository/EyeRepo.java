package com.hawk.gank.model.repository;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.hawk.gank.model.error.RxErrorProcessor;
import com.hawk.gank.model.http.OpenEyeIO;
import com.hawk.gank.model.state.OpenEyeState;
import com.hawk.lib.mvp.qualifiers.ActivityScope;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by heyong on 2018/1/1.
 */
@ActivityScope
public class EyeRepo {
    private final OpenEyeIO mOpenEyeIO;
    private final OpenEyeState mOpenEyeState;
    private final RxErrorProcessor mRxErrorProcessor;

    @Inject
    EyeRepo(final OpenEyeIO openEyeIO, final OpenEyeState openEyeState, final RxErrorProcessor rxErrorProcessor) {
        this.mOpenEyeIO = openEyeIO;
        this.mOpenEyeState = openEyeState;
        this.mRxErrorProcessor = rxErrorProcessor;
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable getOpenEyeData(@NonNull int viewId, @NonNull String date) {
        return mOpenEyeIO.getDailyData(date)
                .subscribeOn(Schedulers.io())
                .map(eyeData -> eyeData.getIssueList().get(0).getItemList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemList -> mOpenEyeState.setOpenEye(viewId, date, itemList),
                        t -> {
                            mRxErrorProcessor.tryWithRxError(t,
                                    rxError -> mOpenEyeState.notifyRxError(viewId, rxError));
                        });
    }

}
