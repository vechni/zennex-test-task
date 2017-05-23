package com.zennex.task.common.mvp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zennex.task.R;
import com.zennex.task.TestApplication;
import com.zennex.task.di.component.ComponentFragment;
import com.zennex.task.di.component.DaggerComponentFragment;
import com.zennex.task.di.module.ModuleFragment;

public abstract class BaseFragment extends Fragment implements MvpBaseView {

    protected Activity activity;
    private ComponentFragment component;
    private ProgressDialog progressDialog;

    // region - Lifecycle -

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    // endregion


    protected ComponentFragment getFragmentComponent() {
        if (component == null) {
            component = DaggerComponentFragment.builder()
                    .moduleFragment(new ModuleFragment(this))
                    .componentApplication(TestApplication.getComponentApplication())
                    .build();
        }
        return component;
    }

    public void setTextTitleToolbar(String title) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        txtTitle.setText(title);
    }

    public void showOnBackPressedToolbar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public void hideOnBackPressedToolbar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }

    public void openWaitDialog(String message, DialogInterface.OnCancelListener listener) {
        closeWaitDialog();

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(message);
        progressDialog.setOnCancelListener(listener);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void closeWaitDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showToastShort(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public void showToastLong(@StringRes int resId) {
        Toast.makeText(activity, getString(resId), Toast.LENGTH_LONG).show();
    }

    public void showToastShort(@StringRes int resId) {
        Toast.makeText(activity, getString(resId), Toast.LENGTH_LONG).show();
    }

    public void hideKeyboard() {
        View view = activity.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void goBack() {
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}
