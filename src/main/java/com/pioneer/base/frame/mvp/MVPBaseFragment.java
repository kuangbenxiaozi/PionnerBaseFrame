package com.pioneer.base.frame.mvp;

import android.os.Bundle;

import com.pioneer.base.frame.base.BaseFragment;

public abstract class MVPBaseFragment<M extends BaseControlModel, V extends BaseViewInterface, P extends BasePresenter<M, V>> extends BaseFragment {
    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == mPresenter) {
            mPresenter = createPresenter();
        }
        mPresenter.attachView((V) this);
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mPresenter != null) {
            mPresenter.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            getLifecycle().removeObserver(mPresenter);
            mPresenter = null;
        }
    }

    protected abstract P createPresenter();
}

