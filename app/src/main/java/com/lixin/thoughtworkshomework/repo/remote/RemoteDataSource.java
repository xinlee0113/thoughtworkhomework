package com.lixin.thoughtworkshomework.repo.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.lixin.thoughtworkshomework.repo.IDataSource;
import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public class RemoteDataSource implements IDataSource {
    private static final String TAG = "RemoteDataSource";
    final MutableLiveData<ProfileEntity> observableProfile = new MutableLiveData<>();
    @NonNull
    final LiveData<PagedList<TweetEntity>> observableTweetList;

    public RemoteDataSource() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(5)  //分页大小
                .setInitialLoadSizeHint(5)  //首次加载大小
                .setPrefetchDistance(5)  //预加载距离：还剩5个就要滑到底了，就进行预加载
                .build();

        DataSource.Factory<Integer, TweetEntity> mFactory = new DataSource.Factory<Integer, TweetEntity>() {
            @NonNull
            @Override
            public DataSource<Integer, TweetEntity> create() {
                return new PageKeyedDataSource<Integer, TweetEntity>() {
                    @Override
                    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, TweetEntity> callback) {
                        TweetApiCreator.getInstance().create()
                                .getTweets("jsmith")
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(new Observer<List<TweetEntity>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
//                                        Log.i(TAG, "fetchTweetList onSubscribe");
                                    }

                                    @Override
                                    public void onNext(@NonNull List<TweetEntity> tweetEntities) {
//                                        Log.i(TAG, "fetchTweetList onNext" + tweetEntities);
                                        List<TweetEntity> data = tweetEntities.subList(0, params.requestedLoadSize - 1);
                                        //前一页为0，后一页5
                                        callback.onResult(data, 0, params.requestedLoadSize);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
//                                        Log.i(TAG, "fetchTweetList onError=" + e.toString());
                                    }

                                    @Override
                                    public void onComplete() {
//                                        Log.i(TAG, "fetchTweetList onComplete");
                                    }
                                });

                    }

                    @Override
                    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TweetEntity> callback) {
                        Log.i(TAG, "loadBefore params key=" + params.key + ",params size" + params.requestedLoadSize);
                    }

                    @Override
                    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TweetEntity> callback) {
                        TweetApiCreator.getInstance().create()
                                .getTweets("jsmith")
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(new Observer<List<TweetEntity>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
//                                        Log.i(TAG, "fetchTweetList onSubscribe");
                                    }

                                    @Override
                                    public void onNext(@NonNull List<TweetEntity> tweetEntities) {
                                        Log.i(TAG, "params key=" + params.key + ",requestLoadSize=" + params.requestedLoadSize);
                                        int subListTo = params.key + params.requestedLoadSize - 1;
                                        List<TweetEntity> pagedList = tweetEntities.subList(params.key, subListTo < tweetEntities.size() ? subListTo : tweetEntities.size() - 1);
                                        callback.onResult(pagedList, params.key + params.requestedLoadSize - 1 < tweetEntities.size() ? params.key + params.requestedLoadSize - 1 : tweetEntities.size() - 1);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
//                                        Log.i(TAG, "fetchTweetList onError=" + e.toString());
                                    }

                                    @Override
                                    public void onComplete() {
//                                        Log.i(TAG, "fetchTweetList onComplete");
                                    }
                                });
                    }
                };
            }
        };
        observableTweetList = new LivePagedListBuilder<Integer, TweetEntity>(mFactory, config).build();
    }

    @NonNull
    @Override
    public LiveData<ProfileEntity> getProfile(String userName) {
        fetchProfile(userName);
        return observableProfile;
    }

    private void fetchProfile(String userName) {
        TweetApiCreator.getInstance().create()
                .getProfile(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProfileEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.i(TAG, "fetchProfile onSubscribe");
                    }

                    @Override
                    public void onNext(ProfileEntity profileEntity) {
//                        Log.i(TAG, "fetchProfile onNext" + profileEntity);
                        observableProfile.setValue(profileEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.i(TAG, "fetchProfile onError=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
//                        Log.i(TAG, "fetchProfile onComplete");
                    }
                });
    }

    @NonNull
    @Override
    public LiveData<PagedList<TweetEntity>> getTweets(String userName) {
        return observableTweetList;
    }

    public void getTweets(DataCallback<List<TweetEntity>> callback) {
        TweetApiCreator.getInstance().create()
                .getTweets("jsmith")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new io.reactivex.Observer<List<TweetEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                                        Log.i(TAG, "fetchTweetList onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull List<TweetEntity> tweetEntities) {
                        callback.onResult(tweetEntities);

                    }

                    @Override
                    public void onError(Throwable e) {
//                                        Log.i(TAG, "fetchTweetList onError=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
//                                        Log.i(TAG, "fetchTweetList onComplete");
                    }
                });
    }

    public interface DataCallback<T> {
        void onResult(T t);
    }
}
