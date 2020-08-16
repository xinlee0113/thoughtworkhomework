package com.lixin.thoughtworkshomework.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.PagedList;

import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;
import com.lixin.thoughtworkshomework.repo.local.dao.AppDataBase;
import com.lixin.thoughtworkshomework.repo.remote.RemoteDataSource;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public class Repository implements IDataSource {
    private static Repository sInstance;
    private final AppDataBase dataBase;
    @NonNull
    private final RemoteDataSource remoteDataSource;
    private MediatorLiveData<List<TweetEntity>> observableTweets;

    private Repository(final AppDataBase database) {
        this.dataBase = database;
//        observableTweets = new MediatorLiveData<>();
//        observableTweets.addSource(database.tweetDao().queryTweetsByUserName("jsmith"),
//                new Observer<List<TweetEntity>>() {
//                    @Override
//                    public void onChanged(List<TweetEntity> productEntities) {
//                        if (database.getDatabaseCreated().getValue() != null) {
//                            observableTweets.postValue(productEntities);
//                        }
//                    }
//                });

        remoteDataSource = new RemoteDataSource();
    }

    public static Repository getInstance(final AppDataBase database) {
        if (sInstance == null) {
            synchronized (Repository.class) {
                if (sInstance == null) {
                    sInstance = new Repository(database);
                }
            }
        }
        return sInstance;
    }

    @NonNull
    @Override
    public LiveData<ProfileEntity> getProfile(String userName) {
        return remoteDataSource.getProfile(userName);
    }

    @NonNull
    @Override
    public LiveData<PagedList<TweetEntity>> getTweets(String userName) {
        return remoteDataSource.getTweets(userName);
    }
}
