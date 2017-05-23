package com.zennex.task.common.lib;

import android.content.Context;
import android.util.TypedValue;

public class Dips {

    public static float toPix(Context context, int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
}
