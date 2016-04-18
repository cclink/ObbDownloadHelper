package com.cclink.obbdownloader.util;

import android.content.Context;

public class ResourceUtil {
    public static int getResourceId(Context context, String resName, String resType) {
        return context.getResources().getIdentifier(resName, resType, context.getPackageName());
    }

    public static String getString(Context context, String resName) {
        return context.getString(getResourceId(context, resName, "string"));
    }
}
