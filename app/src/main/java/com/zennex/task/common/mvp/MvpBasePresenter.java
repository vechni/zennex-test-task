package com.zennex.task.common.mvp;

public interface MvpBasePresenter<V extends MvpBaseView> {

    public void attachView(V view);

    public void detachView();

    public void onStart();

    public void onStop();

    public void onResume();

    public void onPause();
}
