package com.lixin.thoughtworkshomework.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;
import com.lixin.thoughtworkshomework.repo.local.AppDataBase;
import com.lixin.thoughtworkshomework.repo.remote.RemoteDataSource;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lixin
 * @date 2020/8/14.
 * 数据仓，为ui层提供数据， 包装了数据接口、数据加载策略。
 */
public class Repository implements IDataSource {
    private static Repository sInstance;
    private final AppDataBase mLocalDataSource;
    @NonNull
    private final RemoteDataSource mRemoteDataSource;
    private LiveData<PagedList<TweetEntity>> mPagedTweetList;
    /**
     * 十分钟之后判定为需要刷新
     */
    private RateLimiter<String> rateLimiter;

    private Repository(final AppDataBase database) {
        this.mLocalDataSource = database;
        rateLimiter = new RateLimiter<>(10, TimeUnit.MINUTES);
        mPagedTweetList = new MediatorLiveData<>();
        DataSource.Factory<Integer, TweetEntity> factory = database.tweetDao().queryTweetsByUserName();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(5)              // 分页加载的数量
                .setInitialLoadSizeHint(20)   // 初次加载的数量
                .setPrefetchDistance(5)      // 预取数据的距离
                .setEnablePlaceholders(false) // 是否启用占位符（本地数据比较合适，因为远程数据是未知的）
                .build();
        mPagedTweetList = new LivePagedListBuilder<>(factory, config).build();

        mRemoteDataSource = new RemoteDataSource();
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
        return mRemoteDataSource.getProfile(userName);
    }

    @NonNull
    @Override
    public LiveData<PagedList<TweetEntity>> getTweets(String userName, boolean needFetch) {
        freshTweetsIfNeed(userName, needFetch);
        return mPagedTweetList;
    }

    private void freshTweetsIfNeed(String userName, boolean reqFetch) {
        //sync data from cloud (all data, but not the paged), and the save into db,
        // the repository can listened the data changed,ui refreshed;
        //主动同步数据或者数据过期时，需要同步数据。
        if (reqFetch || shouldFetch(userName)) {
            mRemoteDataSource.getTweets(new RemoteDataSource.DataCallback<List<TweetEntity>>() {
                @Override
                public void onResult(List<TweetEntity> tweetEntities) {
                    for (TweetEntity tweet : tweetEntities) {
                        mLocalDataSource.tweetDao().save(tweet);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    rateLimiter.reset(userName);
                }
            });
        }


    }

    private boolean shouldFetch(String userName) {
        return rateLimiter.shouldFetch(userName);
    }
}
