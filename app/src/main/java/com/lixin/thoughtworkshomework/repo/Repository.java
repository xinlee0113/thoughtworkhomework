package com.lixin.thoughtworkshomework.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
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
    private final AppDataBase localDataSource;
    @NonNull
    private final RemoteDataSource remoteDataSource;
    private LiveData<PagedList<TweetEntity>> pagedTweetList;

    private Repository(final AppDataBase database) {
        this.localDataSource = database;
        pagedTweetList = new MediatorLiveData<>();
        DataSource.Factory<Integer, TweetEntity> factory = database.tweetDao().queryTweetsByUserName();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(5)              // 分页加载的数量
                .setInitialLoadSizeHint(20)   // 初次加载的数量
                .setPrefetchDistance(5)      // 预取数据的距离
                .setEnablePlaceholders(false) // 是否启用占位符（本地数据比较合适，因为远程数据是未知的）
                .build();
        pagedTweetList = new LivePagedListBuilder<>(factory, config).build();

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
        freshTweets(userName);
        return pagedTweetList;
    }

    private void freshTweets(String userName) {
        remoteDataSource.getTweets(new RemoteDataSource.DataCallback<List<TweetEntity>>() {
            @Override
            public void onResult(List<TweetEntity> tweetEntities) {
                for (TweetEntity tweet : tweetEntities) {
                    localDataSource.tweetDao().save(tweet);
                }
            }
        });


    }
}
