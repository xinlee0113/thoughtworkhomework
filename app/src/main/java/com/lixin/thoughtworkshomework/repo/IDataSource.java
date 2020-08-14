package com.lixin.thoughtworkshomework.repo;

import androidx.lifecycle.LiveData;

import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/14.
 */
public interface IDataSource {

    LiveData<ProfileEntity> getProfile(String userName);

    LiveData<List<TweetEntity>> getTweets(String userName);
}
