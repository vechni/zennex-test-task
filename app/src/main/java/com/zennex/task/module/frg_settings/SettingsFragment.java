package com.zennex.task.module.frg_settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.zennex.task.R;
import com.zennex.task.common.interfaces.Language;
import com.zennex.task.common.interfaces.OnBackPressedListener;
import com.zennex.task.common.mvp.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends BaseFragment implements SettingsContract.View, OnBackPressedListener {

    public static final String TAG = "tag_settings_frg";

    @BindView(R.id.frg_settings_btn_english) RadioButton btnEnglish;
    @BindView(R.id.frg_settings_btn_russian) RadioButton btnRussian;

    @Inject SettingsPresenter presenter;
    private SettingsRouter router;

    // region - Lifecycle -

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        router = (SettingsRouter) activity;
        getFragmentComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root;

        root = inflater.inflate(R.layout.frg_settings, container, false);

        ButterKnife.bind(this, root);

        initScreen();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    // endregion


    // region - Event handlers -

    @OnClick(R.id.frg_settings_btn_english)
    public void clickBtnEnglish(View view) {
        presenter.saveSettings(Language.ENGLISH);
    }

    @OnClick(R.id.frg_settings_btn_russian)
    public void clickBtnRussian(View view) {
        presenter.saveSettings(Language.RUSSIAN);
    }

    @Override
    public void onBackPressed() {
        router.navigateToMainScreen();
    }

    // endregion


    // region - Contract -

    @Override
    public void showSetupSettings(@Language int code) {

        switch (code) {
            case Language.DEFAULT:
                btnEnglish.setChecked(true);
                break;
            case Language.ENGLISH:
                btnEnglish.setChecked(true);
                break;
            case Language.RUSSIAN:
                btnRussian.setChecked(true);
                break;
        }
    }

    @Override
    public void refreshLanguageInApp() {
        router.restart();
    }

    // endregion

    // region - Methods -

    private void initScreen() {
        setTextTitleToolbar(getString(R.string.title_settings));
    }

    // endregion
}
