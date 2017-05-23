package com.zennex.task.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.zennex.task.common.interfaces.Language;
import com.zennex.task.common.utils.PreferencesUtils;

import io.reactivex.Observable;

public class PreferencesRxImpl implements Preferences.RxPref {

    private final SharedPreferences pref;

    public PreferencesRxImpl(Context context) {
        pref = context.getSharedPreferences(PreferencesUtils.PREFERENCES, Context.MODE_PRIVATE);
    }

    public Observable<Integer> saveLanguage(@Language Integer code) {
        return Observable.just(saveLanguageFunc(code));
    }

    private Integer saveLanguageFunc(Integer value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PreferencesUtils.PREF_LANGUAGE, value);
        editor.apply();
        return value;
    }

    public Observable<Integer> getLanguage() {
        return Observable.just(getLanguageFunc());
    }

    private Integer getLanguageFunc() {
        return pref.getInt(PreferencesUtils.PREF_LANGUAGE, Language.DEFAULT);
    }
}
