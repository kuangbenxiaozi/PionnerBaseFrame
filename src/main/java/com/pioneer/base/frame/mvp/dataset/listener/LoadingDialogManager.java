package com.pioneer.base.frame.mvp.dataset.listener;

public interface LoadingDialogManager {
    void showLoadingDialog(boolean canBeCanceled);

    void showLoadingDialog();

    void dismissLoadingDialog();
}
