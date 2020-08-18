package com.lixin.thoughtworkshomework;

import android.app.Application;
import android.content.Context;

import androidx.test.runner.AndroidJUnitRunner;

/**
 * @author lixin
 * @date 2020/8/17.
 */
class TweetTestRunner extends AndroidJUnitRunner {
    public Application newApplication(ClassLoader cl, String className, Context context) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return super.newApplication(cl, TestApp.class.getName(), context);
    }
}
