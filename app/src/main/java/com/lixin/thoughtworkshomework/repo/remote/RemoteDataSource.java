package com.lixin.thoughtworkshomework.repo.remote;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    MutableLiveData<ProfileEntity> observerbalProfile = new MutableLiveData<>();

    MutableLiveData<List<TweetEntity>> observerbalTweet = new MutableLiveData<>();

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
                        Log.i(TAG, "fetchProfile onSubscribe");
                    }

                    @Override
                    public void onNext(ProfileEntity profileEntity) {
                        Log.i(TAG, "fetchProfile onNext"+profileEntity);
                        observerbalProfile.setValue(profileEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "fetchProfile onError="+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "fetchProfile onComplete");
                    }
                });
    }

    @Override
    public LiveData<List<TweetEntity>> getTweets(String userName) {
        fetchTweetList(userName);
        return observerbalTweet;
    }

    private void fetchTweetList(String userName) {
        TweetApiCreator.getInstance().create()
                .getTweets(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TweetEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "fetchTweetList onSubscribe");
                    }

                    @Override
                    public void onNext(List<TweetEntity> tweetEntities) {
                        Log.i(TAG, "fetchTweetList onNext"+tweetEntities);
                        observerbalTweet.setValue(tweetEntities);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "fetchTweetList onError="+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "fetchTweetList onComplete");
                    }
                });
    }
}
