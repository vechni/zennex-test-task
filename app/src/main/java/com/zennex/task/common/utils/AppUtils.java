package com.zennex.task.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtils {

    public static String getPackageNameApp(Context context) {
        return context.getPackageName();
    }

    public static int getVersionCodeApp(Context context) throws NameNotFoundException {
        String packageName = getPackageNameApp(context);

        PackageManager manager = context.getPackageManager();
        PackageInfo info = manager.getPackageInfo(packageName, 0);
        return info.versionCode;
    }

    public static String getVersionNameApp(Context context) throws NameNotFoundException {
        String packageName = getPackageNameApp(context);

        PackageManager manager = context.getPackageManager();
        PackageInfo info = manager.getPackageInfo(packageName, 0);
        return info.versionName;
    }
}
