package com.zennex.task.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.zennex.task.common.interfaces.Language;
import com.zennex.task.common.utils.PreferencesUtils;

public class PreferencesImpl implements Preferences.ImpPref {

    private final SharedPreferences pref;

    public PreferencesImpl(Context context) {
        pref = context.getSharedPreferences(PreferencesUtils.PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveLanguage(@Language int code) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PreferencesUtils.PREF_LANGUAGE, code);
        editor.apply();
    }

    public int getLanguage() {
        return pref.getInt(PreferencesUtils.PREF_LANGUAGE, Language.DEFAULT);
    }
}
