package com.zennex.task.common.mvp;

import android.support.annotation.NonNull;

import com.zennex.task.TestApplication;
import com.zennex.task.di.component.ComponentPresenter;
import com.zennex.task.di.component.DaggerComponentPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends MvpBaseView> implements MvpBasePresenter<V> {

    private MvpBaseView view;
    private ComponentPresenter component;
    private CompositeDisposable compositeSubscription = new CompositeDisposable();

    protected ComponentPresenter getPresenterComponent() {
        if (component == null) {
            component = DaggerComponentPresenter.builder()
                    .componentApplication(TestApplication.getComponentApplication())
                    .build();
        }

        return component;
    }

    protected void unsubscribeOnDestroy(@NonNull Disposable disposable) {
        compositeSubscription.add(disposable);
    }

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        compositeSubscription.clear();
        view = null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}
