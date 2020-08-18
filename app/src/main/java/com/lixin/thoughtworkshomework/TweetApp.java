/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lixin.thoughtworkshomework;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lixin.library.base.utils.ContextUtils;
import com.lixin.module.moments.BuildConfig;
import com.lixin.thoughtworkshomework.module.repo.Repository;
import com.lixin.thoughtworkshomework.module.repo.local.AppDataBase;


/**
 * Android Application class. Used for accessing singletons.
 *
 * @author lixin
 */
public class TweetApp extends Application {

    private static TweetApp sInstance;

    public static TweetApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLibs();
        sInstance = this;
    }

    private void initLibs() {
        ContextUtils.init(this);
        initARouter();
    }

    /**
     * 初始化ARouter
     */
    private void initARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(this);
    }


}
