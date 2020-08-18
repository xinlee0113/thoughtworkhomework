package com.lixin.library.base.utils;

import android.app.Application;

/**
 * @author lixin
 */
public class ContextUtils {
    private static Application application;

    public static void init(Application application) {
        ContextUtils.application = application;
    }

    public static Application getApp() {
        return application;
    }
}
