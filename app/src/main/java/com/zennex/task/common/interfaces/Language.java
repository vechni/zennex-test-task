package com.zennex.task.common.interfaces;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({Language.DEFAULT, Language.ENGLISH, Language.RUSSIAN})
public @interface Language {

    public static final int DEFAULT = 0;
    public static final int ENGLISH = 1;
    public static final int RUSSIAN = 2;
}