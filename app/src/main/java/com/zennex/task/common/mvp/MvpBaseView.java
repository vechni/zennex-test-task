package com.zennex.task.common.mvp;

import android.content.DialogInterface;
import android.support.annotation.StringRes;

public interface MvpBaseView {

    void openWaitDialog(String message, DialogInterface.OnCancelListener listener);

    void closeWaitDialog();

    void hideKeyboard();

    void showKeyboard();

    void showToastShort(String message);

    void showToastLong(String message);

    void showToastShort(@StringRes int resId);

    void showToastLong(@StringRes int resId);

    void goBack();
}
