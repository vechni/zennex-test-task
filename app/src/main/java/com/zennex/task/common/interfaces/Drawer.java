package com.zennex.task.common.interfaces;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({Drawer.MAP, Drawer.PARSING, Drawer.SCALIING, Drawer.LIST})
public @interface Drawer {

    public static final int MAP = 0;
    public static final int PARSING = 1;
    public static final int SCALIING = 2;
    public static final int LIST = 3;
}