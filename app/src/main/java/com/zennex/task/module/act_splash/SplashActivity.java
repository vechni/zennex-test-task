package com.zennex.task.module.act_splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.zennex.task.R;
import com.zennex.task.common.mvp.BaseActivity;
import com.zennex.task.module.act_navigation.NavigationActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    public static final String TAG = "tag_splash_act";
    private final int CONST_DELAY_SPLASH = 1500;

    @Inject SplashPresenter presenter;

    // region - Lifecycle -

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        presenter.attachView(this);

        setContentView(R.layout.act_splash);

        hideStatusBar();

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    // endregion


    // region - Event handlers -

    // endregion


    // region - Contract -

    @Override
    public void navigateToMainScreen() {
        showMainScreen();
    }

    // endregion


    // region - Methods -

    private void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void showMainScreen() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }, CONST_DELAY_SPLASH);
    }

    // endregion
}
