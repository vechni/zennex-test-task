package com.zennex.task.data.preference;

import com.zennex.task.common.interfaces.Language;

import io.reactivex.Observable;

public interface Preferences {

    interface ImpPref {

        void saveLanguage(@Language int code);

        int getLanguage();
    }

    interface RxPref {

        Observable<Integer> saveLanguage(@Language Integer code);

        Observable<Integer> getLanguage();
    }
}
