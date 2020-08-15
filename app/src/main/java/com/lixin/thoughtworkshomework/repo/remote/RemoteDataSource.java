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

    public RemoteDataSource() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(5)  //分页大小
                .setInitialLoadSizeHint(5)  //首次加载大小
                .setPrefetchDistance(5)  //预加载距离：还剩5个就要滑到底了，就进行预加载
                .build();

        DataSource.Factory<Integer, TweetEntity> mFactory = new DataSource.Factory<Integer, TweetEntity>() {
            @Override
            public DataSource<Integer, TweetEntity> create() {
                return new PageKeyedDataSource<Integer, TweetEntity>() {
                    @Override
                    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, TweetEntity> callback) {
                        Log.i(TAG, "loadInitial,params placeholdersEnabled=" + params.placeholdersEnabled + ",params size" + params.requestedLoadSize);
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
                                    public void onNext(List<TweetEntity> tweetEntities) {
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
                                    public void onNext(List<TweetEntity> tweetEntities) {
//                                        Log.i(TAG, "fetchTweetList onNext" + tweetEntities);
                                        // 9 = 5 + 5 - 1 , data: 5-9 ,size:5 ,nextKey:10
                                        //
                                        int nextKey = params.key + params.requestedLoadSize;
                                        int toPosition = nextKey -1 ;
                                        List<TweetEntity> data = tweetEntities.subList(params.key, toPosition < tweetEntities.size() ? toPosition : tweetEntities.size() - 1);
                                        callback.onResult(data, nextKey <tweetEntities.size()? nextKey :null);
                                        Log.i(TAG,"loadAfter:total="+tweetEntities.size()+",currentKey="+params.key + ",data="+params.key+"~"+(params.key+params.requestedLoadSize-1)+",size="+params.requestedLoadSize+",nextKey="+(params.key+params.requestedLoadSize));

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
        observableTweetList = new LivePagedListBuilder(mFactory, config).build();
    }

    MutableLiveData<ProfileEntity> observerbalProfile = new MutableLiveData<>();

    LiveData<PagedList<TweetEntity>> observableTweetList;

    @Override
    public LiveData<ProfileEntity> getProfile(String userName) {
        fetchProfile(userName);
        return observerbalProfile;
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
                        observerbalProfile.setValue(profileEntity);
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

    @Override
    public LiveData<PagedList<TweetEntity>> getTweets(String userName) {
        return observableTweetList;
    }

}
