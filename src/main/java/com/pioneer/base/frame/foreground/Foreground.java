package com.pioneer.base.frame.foreground;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用来控制前后台切换的工具类
 * public void onCreate(){
 * super.onCreate();
 * Foreground.get(this).addListener(listener);
 * }
 * <p>
 * public void onDestroy(){
 * super.onCreate();
 * Foreground.get(this).removeListener(listener);
 * }
 */
public class Foreground implements Application.ActivityLifecycleCallbacks {

    public static final long CHECK_DELAY = 500;

    public interface AppForegroundListener {

        void onBecameForeground();

        void onBecameBackground();

    }

    private static Foreground instance;

    private boolean foreground = true, paused = true;
    private Handler handler = new Handler();
    private List<AppForegroundListener> listeners = new CopyOnWriteArrayList<AppForegroundListener>();
    private Runnable check;
    private WeakReference<Activity> foreGroundActivity;

    /**
     * @param application
     * @return
     */
    public static Foreground init(Application application) {
        if(instance == null) {
            instance = new Foreground();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public void destroy() {
        if(instance != null) {
            instance = null;
        }
        if(listeners != null) {
            listeners.clear();
        }
    }

    public static Foreground get(Application application) {
        if(instance == null) {
            init(application);
        }
        return instance;
    }

    public static Foreground get(Context context) {
        if(null != context) {
            if(instance == null) {
                Context appCtx = context.getApplicationContext();
                if(appCtx instanceof Application) {
                    return instance = init((Application) appCtx);
                }
                throw new IllegalStateException("Foreground 创建失败了");
            }
            return instance;
        }
        return null;
    }

    public Activity getForegroundActivity() {
        return this.foreGroundActivity.get();
    }

    public boolean isForeground() {
        return foreground;
    }

    public boolean isBackground() {
        return !foreground;
    }

    public void addListener(AppForegroundListener listener) {
        if(listeners != null) {
            listeners.add(listener);
        }
    }

    public void clearListener() {
        if(listeners != null) {
            listeners.clear();
        }
    }

    public void removeListener(AppForegroundListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        boolean wasBackground = !foreground;
        foreground = true;

        if(check != null) handler.removeCallbacks(check);

        this.foreGroundActivity = new WeakReference<Activity>(activity);

        if(wasBackground) {
            //Log.i(TAG, "进入前台 ");
            for(AppForegroundListener l : listeners) {
                try {
                    l.onBecameForeground();
                } catch(Exception exc) {
                    //Log.e(TAG, "Listener threw exception!", exc);
                }
            }
        } else {
            //Log.i(TAG, "still foreground");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        paused = true;

        if(check != null) handler.removeCallbacks(check);

        if(activity == this.foreGroundActivity.get()) {
            this.foreGroundActivity = null;
        }

        handler.postDelayed(check = new Runnable() {
            @Override
            public void run() {
                if(foreground && paused) {
                    foreground = false;
                    //Log.i(TAG, "进入后台");
                    for(AppForegroundListener l : listeners) {
                        try {
                            l.onBecameBackground();
                        } catch(Exception exc) {
                            //Log.e(TAG, "Listener threw exception!", exc);
                        }
                    }
                } else {
                    //Log.i(TAG, "still foreground");
                }
            }
        },CHECK_DELAY);
    }

    @Override
    public void onActivityCreated(Activity activity,Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity,Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}