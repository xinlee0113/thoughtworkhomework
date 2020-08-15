package com.lixin.thoughtworkshomework.ui.tweetlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lixin.thoughtworkshomework.TweetApp;
import com.lixin.thoughtworkshomework.repo.Repository;
import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public class TweetViewModel extends ViewModel {
    Repository repository;
    LiveData<ProfileEntity> observableProfile;

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

//    public static class Factory extends ViewModelProvider.AndroidViewModelFactory {
//
//        /**
//         * Creates a {@code AndroidViewModelFactory}
//         *
//         * @param application an application to pass in {@link AndroidViewModel}
//         */
//        public Factory(@NonNull Application application) {
//            super(application);
//        }
//
//        @NonNull
//        @Override
//        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//            return (T) new TweetViewModel();
//        }
//    }

}
