package com.pioneer.base.frame.mvp.dataset;


import com.pioneer.base.frame.mvp.BasePresenter;
import com.pioneer.base.frame.mvp.dataset.listener.ModelControllerListener;

public abstract class MVPDataSetBasePresenter<M extends MVPDataSetBaseModel, V extends MVPDataSetBaseViewInterface> extends BasePresenter<M, V> implements ModelControllerListener {

}
