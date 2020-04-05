package com.pioneer.base.frame.handle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public class NoLeakHandler implements NoLeakHandlerInterface {

    private final WeakRefHandler _handler;

    public NoLeakHandler() {
        _handler = new WeakRefHandler(this);
    }

    public NoLeakHandler(NoLeakHandlerInterface host) {
        _handler = new WeakRefHandler(host);
    }

    public NoLeakHandler(Looper looper) {
        _handler = new WeakRefHandler(looper, this);
    }

    public NoLeakHandler(Looper looper, NoLeakHandlerInterface host) {
        _handler = new WeakRefHandler(looper, host);
    }

    public final WeakRefHandler handler() {
        return _handler;
    }

    private NoLeakHandlerInterface innerHandler() {
        return _handler._host.get();
    }

    public final void removeMessages(int what) {
        handler().removeMessages(what);
    }

    public final boolean sendEmptyMessageDelayed(int what, long delayMillis) {
        return handler().sendEmptyMessageDelayed(what, delayMillis);
    }

    public final boolean sendEmptyMessageDelayedWithRef(int what,
                                                        long delayMillis) {
        if (innerHandler() != null) {
            Message msg = Message.obtain(handler(), what, innerHandler());
            return handler().sendMessageDelayed(msg, delayMillis);
        }
        return false;
    }

    public final Message obtainMessage(int what, int arg1, int arg2) {
        return handler().obtainMessage(what, arg1, arg2);
    }

    public final boolean sendMessage(Message msg) {
        return handler().sendMessage(msg);
    }

    public final boolean sendEmptyMessage(int what) {
        return handler().sendEmptyMessage(what);
    }

    public final void removeCallbacksAndMessages(Object token) {
        handler().removeCallbacksAndMessages(token);
    }

    public final boolean hasMessages(int what) {
        return handler().hasMessages(what);
    }

    public final Message obtainMessage(int what, Object obj) {
        return handler().obtainMessage(what, obj);
    }

    public final Message obtainMessage(int what, int arg1, int arg2, Object obj) {
        return handler().obtainMessage(what, arg1, arg2, obj);
    }

    public final Looper getLooper() {
        return handler().getLooper();
    }

    public final boolean sendMessageDelayed(Message msg, long delayMillis) {
        return handler().sendMessageDelayed(msg, delayMillis);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void handleMessage(Message msg) {
    }

    public static class WeakRefHandler extends Handler {
        private final WeakReference<NoLeakHandlerInterface> _host;

        public WeakRefHandler(NoLeakHandlerInterface host) {
            _host = new WeakReference<NoLeakHandlerInterface>(host);
        }

        public WeakRefHandler(Looper looper, NoLeakHandlerInterface host) {
            super(looper);
            _host = new WeakReference<NoLeakHandlerInterface>(host);
        }

        @Override
        public void handleMessage(Message msg) {
            NoLeakHandlerInterface host = _host.get();
            if (null != host && host.isValid()) {
                host.handleMessage(msg);
            }
        }

        public void handleMessage(int what) {
            NoLeakHandlerInterface host = _host.get();
            if (null != host && host.isValid()) {
                host.handleMessage(obtainMessage(what));
            }
        }

        public boolean isHostReachable() {
            return _host.get() != null;
        }
    }
}
