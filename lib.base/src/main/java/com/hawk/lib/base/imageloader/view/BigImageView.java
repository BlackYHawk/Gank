/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.hawk.lib.base.imageloader.view;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hawk.lib.base.imageloader.BigImageViewer;
import com.hawk.lib.base.imageloader.indicator.ProgressIndicator;
import com.hawk.lib.base.imageloader.loader.ImageLoader;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Piasy{github.com/Piasy} on 06/11/2016.
 *
 * Use FrameLayout for extensibility.
 */

public class BigImageView extends FrameLayout implements ImageLoader.Callback {
    private final SubsamplingScaleImageView mImageView;

    private final ImageLoader mImageLoader;
    private final List<File> mTempImages;

    private View mProgressIndicatorView;
    private View mThumbnailView;

    private Disposable mPermissionRequest;
    private ImageSaveCallback mImageSaveCallback;
    private File mCurrentImageFile;
    private Uri mThumbnail;

    private ProgressIndicator mProgressIndicator;
    private final ProgressNotifyRunnable mProgressNotifyRunnable
            = new ProgressNotifyRunnable() {
        @Override
        public void run() {
            if (mProgressIndicator != null) {
                mProgressIndicator.onProgress(mProgress);
                notified();
            }
        }
    };

    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mImageView = new SubsamplingScaleImageView(context, attrs);
        addView(mImageView);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mImageView.setLayoutParams(params);
        mImageView.setMinimumTileDpi(160);
        mImageView.setOnImageEventListener(new DisplayOptimizeListener(mImageView));

        mImageLoader = BigImageViewer.imageLoader();

        mTempImages = new ArrayList<>();
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        mImageView.setOnClickListener(listener);
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener listener) {
        mImageView.setOnLongClickListener(listener);
    }

    public void setImageSaveCallback(ImageSaveCallback imageSaveCallback) {
        mImageSaveCallback = imageSaveCallback;
    }

    public void setProgressIndicator(ProgressIndicator progressIndicator) {
        mProgressIndicator = progressIndicator;
    }

    public String currentImageFile() {
        return mCurrentImageFile == null ? "" : mCurrentImageFile.getAbsolutePath();
    }

    public void saveImageIntoGallery() {
        if (mCurrentImageFile == null) {
            if (mImageSaveCallback != null) {
                mImageSaveCallback.onFail(new IllegalStateException("image not downloaded yet"));
            }

            return;
        }

        disposePermissionRequest();
        mPermissionRequest = RxPermissions.getInstance(getContext())
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean grant) throws Exception {
                        if (grant) {
                            doSaveImageIntoGallery();
                        } else {
                            if (mImageSaveCallback != null) {
                                mImageSaveCallback.onFail(
                                        new RuntimeException("Permission denied"));
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mImageSaveCallback != null) {
                            mImageSaveCallback.onFail(throwable);
                        }
                    }
                });
    }

    private void doSaveImageIntoGallery() {
        try {
            String result = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                    mCurrentImageFile.getAbsolutePath(), mCurrentImageFile.getName(), "");
            if (mImageSaveCallback != null) {
                if (!TextUtils.isEmpty(result)) {
                    mImageSaveCallback.onSuccess(result);
                } else {
                    mImageSaveCallback.onFail(new RuntimeException("saveImageIntoGallery fail"));
                }
            }
        } catch (FileNotFoundException e) {
            if (mImageSaveCallback != null) {
                mImageSaveCallback.onFail(e);
            }
        }
    }

    private void disposePermissionRequest() {
        if (mPermissionRequest != null && !mPermissionRequest.isDisposed()) {
            mPermissionRequest.dispose();
            mPermissionRequest = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        for (int i = 0, size = mTempImages.size(); i < size; i++) {
            mTempImages.get(i).delete();
        }
        mTempImages.clear();
        disposePermissionRequest();
    }

    public void showImage(Uri uri) {
        Log.d("BigImageView", "showImage " + uri);

        mThumbnail = Uri.EMPTY;
        mImageLoader.loadImage(uri, this);
    }

    public void showImage(Uri thumbnail, Uri uri) {
        Log.d("BigImageView", "showImage with thumbnail " + thumbnail + ", " + uri);

        mThumbnail = thumbnail;
        mImageLoader.loadImage(uri, this);
    }

    @UiThread
    @Override
    public void onCacheHit(File image) {
        Log.d("BigImageView", "onCacheHit " + image);

        mCurrentImageFile = image;
        doShowImage(image);
    }

    @WorkerThread
    @Override
    public void onCacheMiss(final File image) {
        Log.d("BigImageView", "onCacheMiss " + image);

        mCurrentImageFile = image;
        mTempImages.add(image);
        post(new Runnable() {
            @Override
            public void run() {
                doShowImage(image);
            }
        });
    }

    @WorkerThread
    @Override
    public void onStart() {
        post(new Runnable() {
            @Override
            public void run() {
                if (mThumbnail != Uri.EMPTY) {
                    mThumbnailView = mImageLoader.showThumbnail(BigImageView.this, mThumbnail);
                    addView(mThumbnailView);
                }

                if (mProgressIndicator != null) {
                    mProgressIndicatorView = mProgressIndicator.getView(BigImageView.this);
                    addView(mProgressIndicatorView);
                    mProgressIndicator.onStart();
                }
            }
        });
    }

    @WorkerThread
    @Override
    public void onProgress(final int progress) {
        if (mProgressIndicator != null && mProgressNotifyRunnable.update(progress)) {
            post(mProgressNotifyRunnable);
        }
    }

    @WorkerThread
    @Override
    public void onFinish() {
        post(new Runnable() {
            @Override
            public void run() {
                if (mThumbnailView != null) {
                    mThumbnailView.setVisibility(GONE);
                }

                if (mProgressIndicator != null) {
                    mProgressIndicator.onFinish();
                }

                if (mProgressIndicatorView != null) {
                    mProgressIndicatorView.setVisibility(GONE);
                }
            }
        });
    }

    @UiThread
    private void doShowImage(File image) {
        mImageView.setImage(ImageSource.uri(Uri.fromFile(image)));
    }
}
