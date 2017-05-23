package com.zennex.task.module.frg_list_dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zennex.task.R;
import com.zennex.task.TestApplication;
import com.zennex.task.data.DataLayer;
import com.zennex.task.di.component.ComponentPresenter;
import com.zennex.task.di.component.DaggerComponentPresenter;
import com.zennex.task.model.ListItem;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListDialogPresenter implements ListDialogContract.Presenter {

    public static final String TAG = "tag_list_dialog_pres";

    private ComponentPresenter component;
    private CompositeDisposable compositeSubscription = new CompositeDisposable();

    private ListDialogContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    @Inject
    public ListDialogPresenter() {

    }

    // region - Lifecycle -

    public void attachView(ListDialogContract.View view) {
        this.view = view;
        if (component == null) {
            component = DaggerComponentPresenter.builder()
                    .componentApplication(TestApplication.getComponentApplication())
                    .build();
        }
        component.inject(this);
    }

    public void detachView() {
        compositeSubscription.clear();
        view = null;
    }

    // endregion


    // region - Contract -

    @Override
    public void removeItem(ListItem item, int position) {
        unsubscribeOnDestroy(removeItemFromDb(item, position));
    }

    // endregion


    // region - Methods -

    private Disposable removeItemFromDb(ListItem item, int position) {
        return dataLayer.repListItem.remove(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    view.removeItemFromList(position);
                }, error -> {
                    String message = context.getString(R.string.txt_error);
                    view.showMessage(message);
                });
    }

    private void unsubscribeOnDestroy(@NonNull Disposable disposable) {
        compositeSubscription.add(disposable);
    }

    // endregion
}
