package com.zennex.task.module.frg_list;

import android.content.Context;

import com.zennex.task.R;
import com.zennex.task.common.mvp.BasePresenter;
import com.zennex.task.data.DataLayer;
import com.zennex.task.model.ListItem;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListPresenter extends BasePresenter<ListContract.View> implements ListContract.Presenter {

    public static final String TAG = "tag_list_pres";

    private ListContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    @Inject
    public ListPresenter() {

    }

    // region - Lifecycle -

    @Override
    public void attachView(ListContract.View view) {
        super.attachView(view);
        this.view = view;
        getPresenterComponent().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    // endregion


    // region - Contract -

    @Override
    public void loadData() {
        unsubscribeOnDestroy(getDataFromDb());
    }

    @Override
    public void updateItem(ListItem item, int position) {
        unsubscribeOnDestroy(updateItemInDB(item, position));
    }

    // endregion


    // region - Methods -

    private Disposable getDataFromDb() {
        return dataLayer.repListItem.fetchAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    view.showList(result);
                }, error -> {

                });
    }

    private Disposable updateItemInDB(ListItem item, int position) {
        boolean state = item.isState();
        item.setState(!state);

        return dataLayer.repListItem.update(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    view.refreshChangedItem(position);
                }, error -> {
                    error.printStackTrace();
                    showError();
                });
    }

    private void showError() {
        String message = context.getString(R.string.txt_error);
        view.showMessage(message);
    }

    // endregion
}
