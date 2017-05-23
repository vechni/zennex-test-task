package com.zennex.task.module.act_navigation;

import android.content.Context;
import android.content.res.Configuration;

import com.zennex.task.common.interfaces.Language;
import com.zennex.task.common.mvp.BasePresenter;
import com.zennex.task.data.DataLayer;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {

    public static final String TAG = "tag_nav_pres";

    private NavigationContract.View view;
    @Inject DataLayer dataLayer;
    @Inject Context context;

    @Inject
    public NavigationPresenter() {

    }

    // region - Lifecycle -

    @Override
    public void attachView(NavigationContract.View view) {
        super.attachView(view);
        this.view = view;
        getPresenterComponent().inject(this);
        setSelectedLanguage();
    }

    // endregion


    // region - Contract -

    @Override
    public void setSelectedLanguage() {
        int codeLanguage = dataLayer.prefImp.getLanguage();
        setLocale(codeLanguage);
    }

    // endregion


    // region - Methods -

    private void setLocale(int codeLanguage) {
        Locale locale = null;

        switch (codeLanguage) {
            case Language.ENGLISH:
                locale = new Locale("en");
                break;
            case Language.RUSSIAN:
                locale = new Locale("ru");
                break;
            default:
                locale = new Locale("en");
                break;
        }

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, null);
    }

    // endregion
}