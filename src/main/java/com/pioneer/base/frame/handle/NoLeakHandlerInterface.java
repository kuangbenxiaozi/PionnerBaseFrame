package com.pioneer.base.frame.handle;

import android.os.Message;

public interface NoLeakHandlerInterface {

    boolean isValid();

    void handleMessage(Message msg);
}
