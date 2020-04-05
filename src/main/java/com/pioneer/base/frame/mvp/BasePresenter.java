package com.pioneer.base.frame.mvp;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;

import com.pioneer.base.frame.foreground.Foreground;

import java.lang.ref.WeakReference;

/**
 * MVP模式Presenter基类
 */
public abstract class BasePresenter<M extends BaseControlModel,V extends BaseViewInterface> implements LifecycleObserver, Foreground.AppForegroundListener {
    protected WeakReference<V> mViewRef;
    protected boolean foreground = true;
    protected M model;

    public BasePresenter() {
        model = createModel();
    }

    public void attachView(V view) {
        if(null != view) {
            mViewRef = new WeakReference<V>(view);

            Foreground foreground = Foreground.get(getContext());
            if(null != foreground) {
                foreground.addListener(this);
            }
        }
    }

    public void detachView() {
        if(mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;

            Foreground foreground = Foreground.get(getContext());
            if(null != foreground) {
                foreground.removeListener(this);
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if(null != model) {
            model.onCreate();
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
        detachView();
        if(null != model) {
            model.onDestroy();
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
    }

    public boolean isViewAttached() {
        return null != mViewRef && null != mViewRef.get();
    }

    public V getViewInterface() {
        if(isViewAttached()) {
            return mViewRef.get();
        } else {
            detachView();
            return null;
        }
    }

    protected Activity getActivity() {
        V t = getViewInterface();
        if(t instanceof Activity) {
            return (Activity) t;
        } else if(t instanceof Fragment) {
            return ((Fragment) t).getActivity();
        }
        return null;
    }

    protected Fragment getFragment() {
        V t = getViewInterface();
        if(t instanceof Fragment) {
            return (Fragment) t;
        }
        return null;
    }

    protected Context getContext() {
        V t = getViewInterface();
        if(t instanceof Fragment) {
            return ((Fragment) t).getActivity();
        } else if(t instanceof Context) {
            return (Context) t;
        }
        return null;
    }

    protected Resources getResources() {
        V t = getViewInterface();
        if(t instanceof Fragment) {
            return ((Fragment) t).getResources();
        } else if(t instanceof Context) {
            return ((Context) t).getResources();
        }
        return null;
    }

    protected abstract M createModel();

    public void onBecameForeground() {

    }

    public void onBecameBackground() {

    }
}

