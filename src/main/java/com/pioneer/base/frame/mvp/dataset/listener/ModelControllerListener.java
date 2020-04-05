package com.pioneer.base.frame.mvp.dataset.listener;

public interface ModelControllerListener {

    void onRefreshComplete(Object obj);

    void onRefreshFail(Object obj);

    void onLoadNextComplete(boolean hasMore,Object obj);

    void onLoadNextFail(Object obj);

    void onNoDataFound(); // 服务器返回没有数据/响应

    void onLoadDataDone(); // 所有数据都已经加载完了
}
