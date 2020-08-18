package com.lixin.thoughtworkshomework.module.moments.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.lixin.library.base.utils.ContextUtils;
import com.lixin.thoughtworkshomework.AppExecutors;
import com.lixin.thoughtworkshomework.module.moments.repo.Repository;
import com.lixin.thoughtworkshomework.module.moments.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.module.moments.repo.entity.TweetEntity;
import com.lixin.thoughtworkshomework.module.moments.repo.local.AppDataBase;

/**
 * @author lixin
 * @date 2020/8/14.
 * 朋友圈ViewModel
 */
public class MomentsViewModel extends ViewModel {
    private static final String TAG = "TweetViewModel";
    final Repository mRepository;
    LiveData<ProfileEntity> mObservableProfile;
    LiveData<PagedList<TweetEntity>> observableTweetList;

    public MomentsViewModel() {
        super();
        mRepository = Repository.getInstance(
                AppDataBase.getInstance(ContextUtils.getApp(),
                        AppExecutors.getInstance()));
    }

    public LiveData<ProfileEntity> getObservableProfile(String userName) {
        if (mObservableProfile == null) {
            mObservableProfile = mRepository.getProfile(userName);
        }
        return mObservableProfile;
    }

    public LiveData<PagedList<TweetEntity>> getObservableTweetList(String userName, boolean reqFetch) {
        if (null == observableTweetList) {
            observableTweetList = mRepository.getTweets(userName, reqFetch);
        }
        return observableTweetList;
    }

}
