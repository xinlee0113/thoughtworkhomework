package com.lixin.thoughtworkshomework.repo.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
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
 * 远程数据源
 */
public class RemoteDataSource implements IDataSource {
    private static final String TAG = "RemoteDataSource";
    final MutableLiveData<ProfileEntity> mObservableProfile;
    @NonNull
    final LiveData<PagedList<TweetEntity>> mObservableTweetList;

    public RemoteDataSource() {
        mObservableProfile = new MutableLiveData<>();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(5)  //分页大小
                .setInitialLoadSizeHint(5)  //首次加载大小
                .setPrefetchDistance(5)  //预加载距离：还剩5个就要滑到底了，就进行预加载
                .build();

        mObservableTweetList = new LivePagedListBuilder<Integer, TweetEntity>
                (new RemoteDataSourceStub.Factory(), config).build();
    }

    @NonNull
    @Override
    public LiveData<ProfileEntity> getProfile(String userName) {
        fetchProfile(userName);
        return mObservableProfile;
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
                        mObservableProfile.setValue(profileEntity);
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
        return mObservableTweetList;
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
        /**
         * @param result 返回结果
         *               用于通过回调返回结果
         */
        void onResult(T result);
    }
}
