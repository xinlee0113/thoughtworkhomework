package com.lixin.thoughtworkshomework.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lixin.thoughtworkshomework.TweetApp;
import com.lixin.thoughtworkshomework.repo.Repository;
import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public class TweetViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<ProfileEntity> observableProfile;
    LiveData<List<TweetEntity>> observableTweet;

    public TweetViewModel(@NonNull Application application) {
        super(application);
        repository = ((TweetApp) application).getRepository();
    }

    public LiveData<ProfileEntity> getObservableProfile(String userName) {
        if (observableProfile == null) {
            observableProfile = repository.getProfile(userName);
        }
        return observableProfile;
    }

    public LiveData<List<TweetEntity>> getObservableTweet(String userName) {
        if (observableTweet == null) {
            observableTweet = repository.getTweets(userName);

        }
        return observableTweet;
    }

    public static class Factory extends ViewModelProvider.AndroidViewModelFactory {
        Application application;

        /**
         * Creates a {@code AndroidViewModelFactory}
         *
         * @param application an application to pass in {@link AndroidViewModel}
         */
        public Factory(@NonNull Application application) {
            super(application);
            this.application = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TweetViewModel(application);
        }
    }

}
