package com.lixin.thoughtworkshomework.module.moments.repo.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.lixin.thoughtworkshomework.module.moments.repo.entity.TweetEntity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lixin
 * @date 2020/8/17.
 * 基于 PagedList 的分页数据源
 */
public class RemoteDataSourceStub extends PageKeyedDataSource<Integer, TweetEntity> {
    private static final String TAG = "RemoteDataSourceStub";

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

    public static class Factory extends DataSource.Factory<Integer, TweetEntity> {

        @Override
        public DataSource<Integer, TweetEntity> create() {
            return new RemoteDataSourceStub();
        }
    }

}
