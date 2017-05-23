package com.zennex.task.module.frg_parsing;

import android.content.Context;

import com.zennex.task.R;
import com.zennex.task.common.mvp.BasePresenter;
import com.zennex.task.data.DataLayer;
import com.zennex.task.model.Parsing;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ParsingPresenter extends BasePresenter<ParsingContract.View> implements ParsingContract.Presenter {

    public static final String TAG = "tag_parsing_main_pres";

    private ParsingContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    @Inject
    public ParsingPresenter() {

    }

    // region - Lifecycle -

    @Override
    public void attachView(ParsingContract.View view) {
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

    private void loadData() {
        if (isNotNetworkConnection()) return;
        view.startWaitDialog();
        unsubscribeOnDestroy(requestData());
    }

    @Override
    public void refreshData() {
        if (isNotNetworkConnection()) return;
        view.startAnimationRefreshData();
        unsubscribeOnDestroy(requestData());
    }

    // endregion


    // region - Methods -

    private boolean isNotNetworkConnection() {
        if (!dataLayer.restApi.isNetworkConnection()) {
            String message = context.getString(R.string.warning_not_network_connection);
            view.showMessage(message);
            return true;
        }

        return false;
    }

    private Disposable requestData() {
        return dataLayer.restApi.requestParsing(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (validateReceivedData(response)) {
                        view.showListParsing(response.getQuotes());
                    }
                    stopAnimationWaiting();
                }, e -> {
                    stopAnimationWaiting();
                    String message = context.getString(R.string.txt_error);
                    view.showMessage(message);
                });
    }

    private boolean validateReceivedData(Parsing response) {
        if (response == null) {
            showErrorMessage();
            return false;
        }

        if (response.getQuotes().isEmpty()) {
            showErrorMessage();
            return false;
        }

        return true;
    }

    private void showErrorMessage() {
        String message = context.getString(R.string.warning_not_data);
        view.showMessage(message);
    }

    private void stopAnimationWaiting() {
        view.finishWaitDialog();
        view.finishAnimationRefreshData();
    }

    // endregion
}

