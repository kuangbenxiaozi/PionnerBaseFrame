package com.pioneer.base.frame.mvp.dataset;


import com.pioneer.base.frame.mvp.BaseViewInterface;

public interface MVPDataSetBaseViewInterface extends BaseViewInterface {
    void showLoadingMore(boolean isLoading);

    void showLoadingMoreError();

    void onServiceStop(String errorContent);

    void loadFinished();
}
