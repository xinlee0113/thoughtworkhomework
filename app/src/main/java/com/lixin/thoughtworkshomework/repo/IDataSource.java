package com.lixin.thoughtworkshomework.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public interface IDataSource {

    @NonNull
    LiveData<ProfileEntity> getProfile(String userName);

    @NonNull
    LiveData<PagedList<TweetEntity>> getTweets(String userName);
}
