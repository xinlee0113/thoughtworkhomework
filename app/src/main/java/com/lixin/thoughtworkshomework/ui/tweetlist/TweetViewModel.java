package com.lixin.thoughtworkshomework.ui.tweetlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.lixin.thoughtworkshomework.TweetApp;
import com.lixin.thoughtworkshomework.repo.Repository;
import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

/**
 * @author lixin
 * @date 2020/8/14.
 * 朋友圈ViewModel
 */
public class TweetViewModel extends ViewModel {
    private static final String TAG = "TweetViewModel";
    final Repository mRepository;
    LiveData<ProfileEntity> mObservableProfile;
    LiveData<PagedList<TweetEntity>> observableTweetList;

    public TweetViewModel() {
        super();
        mRepository = TweetApp.getInstance().getRepository();
    }

    public LiveData<ProfileEntity> getObservableProfile(String userName) {
        if (mObservableProfile == null) {
            mObservableProfile = mRepository.getProfile(userName);
        }
        return mObservableProfile;
    }

    public LiveData<PagedList<TweetEntity>> getObservableTweetList(String userName) {
        if (null == observableTweetList) {
            observableTweetList = mRepository.getTweets(userName);
        }
        return observableTweetList;
    }

}
