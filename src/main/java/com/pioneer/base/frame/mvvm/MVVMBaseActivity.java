package com.pioneer.base.frame.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.pioneer.base.frame.base.BaseActivity;


/**
 * MVVM模式 Activity基类
 * 泛型中第一个类型是ViewModel,第二个是Layout文件父类ViewDataBinding
 */
public abstract class MVVMBaseActivity<VM extends BaseViewModel<DB>, DB extends ViewDataBinding> extends BaseActivity {
    protected VM mViewModel;
    protected DB viewDataBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == mViewModel) {
            mViewModel = createViewModel();
        }

        if (null == viewDataBinding) {
            viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        }

        if (null != mViewModel && null != viewDataBinding) {
            mViewModel.setDataBinding(viewDataBinding);
        }

        getLifecycle().addObserver(mViewModel);
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

