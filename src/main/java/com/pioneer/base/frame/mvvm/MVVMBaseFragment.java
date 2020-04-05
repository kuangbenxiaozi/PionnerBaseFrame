package com.pioneer.base.frame.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pioneer.base.frame.base.BaseFragment;

/**
 * MVVM模式 Fragment基类
 * 泛型中第一个类型是ViewModel,第二个是Layout文件父类ViewDataBinding
 */
public abstract class MVVMBaseFragment<VM extends BaseViewModel<DB>, DB extends ViewDataBinding> extends BaseFragment {

    protected VM mViewModel;
    protected DB viewDataBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == mViewModel) {
            mViewModel = createViewModel();
        }

        if (null == viewDataBinding && null != getActivity()) {
            viewDataBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), getLayoutId(), null, false);
        }

        if (null != mViewModel && null != viewDataBinding) {
            mViewModel.setDataBinding(viewDataBinding);
        }

        getLifecycle().addObserver(mViewModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != viewDataBinding) {
            return viewDataBinding.getRoot();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (null != mViewModel) {
            mViewModel.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mViewModel) {
            getLifecycle().removeObserver(mViewModel);
            mViewModel = null;
        }

        if (null != viewDataBinding) {
            viewDataBinding = null;
        }
    }

    protected abstract VM createViewModel();

    protected abstract int getLayoutId();
}

