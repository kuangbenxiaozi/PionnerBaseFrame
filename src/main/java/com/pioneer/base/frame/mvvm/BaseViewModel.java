package com.pioneer.base.frame.mvvm;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.ViewDataBinding;

import com.pioneer.base.frame.foreground.Foreground;

/**
 * MVVM模式ViewModel基类
 */
public abstract class BaseViewModel<DB extends ViewDataBinding> implements LifecycleObserver, Foreground.AppForegroundListener {
    protected DB dataBinding;
    protected boolean foreground = true;

    protected void setDataBinding(DB dataBinding) {
        this.dataBinding = dataBinding;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Foreground foreground = Foreground.get(getContext());
        if(null != foreground) {
            foreground.addListener(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        foreground = true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        foreground = false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Foreground foreground = Foreground.get(getContext());
        if(null != foreground) {
            foreground.removeListener(this);
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
    }

    protected Activity getActivity() {
        Context context = getContext();
        if (context != null) {
            return (Activity) context;
        }
        return null;
    }

    protected Context getContext() {
        if (dataBinding != null) {
            return dataBinding.getRoot().getContext();
        }
        return null;
    }

    protected Resources getResources() {
        Context context = getContext();
        if (context != null) {
            context.getResources();
        }
        return null;
    }

    public void onBecameForeground() {

    }

    public void onBecameBackground() {

    }
}

