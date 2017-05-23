package com.zennex.task.common.lib;

import android.content.Context;
import android.util.TypedValue;

public class Pixels {

    public static float toDip(Context context, int pix) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pix, context.getResources().getDisplayMetrics());
    }
}
