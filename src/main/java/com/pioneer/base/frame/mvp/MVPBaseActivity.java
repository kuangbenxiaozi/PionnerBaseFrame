package com.pioneer.base.frame.mvp;

import android.os.Bundle;

import com.pioneer.base.frame.base.BaseActivity;


public abstract class MVPBaseActivity<M extends BaseControlModel, V extends BaseViewInterface, P extends BasePresenter<M, V>> extends BaseActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == mPresenter) {
            mPresenter = createPresenter();
        }
        mPresenter.attachView((V) this);
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            getLifecycle().removeObserver(mPresenter);
            mPresenter = null;
        }
    }

    protected abstract P createPresenter();
}
