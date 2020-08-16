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
 */
public class TweetViewModel extends ViewModel {
    private static final String TAG = "TweetViewModel";
    final Repository repository;
    LiveData<ProfileEntity> observableProfile;
    LiveData<PagedList<TweetEntity>> observableTweetList;

    public TweetViewModel() {
        super();
        repository = TweetApp.getInstance().getRepository();
    }

    public LiveData<ProfileEntity> getObservableProfile(String userName) {
        if (observableProfile == null) {
            observableProfile = repository.getProfile(userName);
        }
        return observableProfile;
    }

    public LiveData<PagedList<TweetEntity>> getObservableTweetList(String userName) {
        if (null == observableTweetList) {
            observableTweetList = repository.getTweets(userName);
        }
        return observableTweetList;
    }

}
