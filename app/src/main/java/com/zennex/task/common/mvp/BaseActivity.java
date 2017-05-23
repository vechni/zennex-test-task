package com.zennex.task.common.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.zennex.task.R;
import com.zennex.task.TestApplication;
import com.zennex.task.di.component.ComponentActivity;
import com.zennex.task.di.component.DaggerComponentActivity;
import com.zennex.task.di.module.ModuleActivity;

public abstract class BaseActivity extends AppCompatActivity implements MvpBaseView {

    private ComponentActivity component;
    private ProgressDialog progressDialog;

    protected ComponentActivity getActivityComponent() {
        if (component == null) {
            component = DaggerComponentActivity.builder()
                    .moduleActivity(new ModuleActivity(this))
                    .componentApplication(TestApplication.getComponentApplication())
                    .build();
        }
        return component;
    }

    protected void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // region - Contract -

    public void openWaitDialog(String message, DialogInterface.OnCancelListener listener) {
        closeWaitDialog();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setOnCancelListener(listener);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void closeWaitDialog() {
        if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
    }

    public void showToastShort(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showToastLong(@StringRes int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    public void showToastShort(@StringRes int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void goBack() {
        finish();
    }

    // endregion
}
