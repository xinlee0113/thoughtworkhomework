package com.lixin.thoughtworkshomework.repo;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public interface IDataSource {

    LiveData<ProfileEntity> getProfile(String userName);

    LiveData<PagedList<TweetEntity>> getTweets(String userName);
}
