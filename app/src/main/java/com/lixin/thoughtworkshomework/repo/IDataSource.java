package com.lixin.thoughtworkshomework.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.lixin.thoughtworkshomework.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

/**
 * @author lixin
 * @date 2020/8/14.
 * 数据源接口
 */
public interface IDataSource {

    /**
     * @param userName 用户名
     * @return 用户信息
     * 获取用户信息
     */
    @NonNull
    LiveData<ProfileEntity> getProfile(String userName);

    /**
     * @param userName 用户名
     * @return 朋友圈列表
     * 获取朋友圈列表
     */
    @NonNull
    LiveData<PagedList<TweetEntity>> getTweets(String userName,boolean fetch);
}
