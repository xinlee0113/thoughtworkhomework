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

import com.lixin.thoughtworkshomework.repo.Repository;
import com.lixin.thoughtworkshomework.repo.local.dao.AppDataBase;


/**
 * Android Application class. Used for accessing singletons.
 *
 * @author lixin
 */
public class TweetApp extends Application {

    private AppExecutors mAppExecutors;
    private static TweetApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
        sInstance = this;
    }

    public static TweetApp getInstance() {
        return sInstance;
    }

    //
    public AppDataBase getDatabase() {
        return AppDataBase.getInstance(this, mAppExecutors);
    }

    public Repository getRepository() {
        return Repository.getInstance(getDatabase());
    }
}
