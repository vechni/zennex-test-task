package com.zennex.task.module.frg_list_edit;

import android.content.Context;

import com.zennex.task.R;
import com.zennex.task.common.mvp.BasePresenter;
import com.zennex.task.data.DataLayer;
import com.zennex.task.model.ListItem;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListEditPresenter extends BasePresenter<ListEditContract.View> implements ListEditContract.Presenter {

    public static final String TAG = "tag_list_edit_pres";

    private ListEditContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    @Inject
    public ListEditPresenter() {

    }

    // region - Lifecycle -

    @Override
    public void attachView(ListEditContract.View view) {
        super.attachView(view);
        this.view = view;
        getPresenterComponent().inject(this);
    }

    // endregion


    // region - Contract -

    @Override
    public void saveChangedItem(ListItem item) {
        if (isNotCorrectChangedData(item)) return;
        unsubscribeOnDestroy(saveItemInDb(item));
    }

    // endregion


    // region - Methods -

    private boolean isNotCorrectChangedData(ListItem item) {
        if (item.getName() == null) {
            showError();
            return true;
        }

        if (item.getName().isEmpty()) {
            showError();
            return true;
        }

        return false;
    }

    private void showError() {
        String message = context.getString(R.string.warning_error_name_is_empty);
        view.showMessage(message);
    }

    private Disposable saveItemInDb(ListItem item) {
        return dataLayer.repListItem.update(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    String message = context.getString(R.string.message_changes_is_successfully);
                    view.showMessage(message);
                    view.goBackScreen();
                }, e -> {
                    String message = context.getString(R.string.txt_error);
                    view.showMessage(message);
                });
    }

    // endregion
}
