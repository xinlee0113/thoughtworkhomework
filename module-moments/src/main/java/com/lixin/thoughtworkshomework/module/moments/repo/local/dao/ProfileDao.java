package com.lixin.thoughtworkshomework.module.moments.repo.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lixin.thoughtworkshomework.module.moments.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.module.moments.repo.entity.TweetEntity;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/13.
 * 用户信息Dao
 */
@Dao
public interface ProfileDao {

    /**
     * @param userName 用户名
     * @return 用户简介
     * 通过用户名查询用户简介信息
     */
    @Query("SELECT * FROM profile WHERE userName=:userName")
    LiveData<ProfileEntity> findProfileByName(String userName);

    /**
     * @param userName 用户名
     * @return 朋友圈列表
     * 通过用户名查询朋友圈列表
     */
    @Query("SELECT * FROM tweet WHERE userName=:userName")
    LiveData<List<TweetEntity>> queryTweetsByUserName(String userName);

    /**
     * @param profiles 插入用户简介信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProfileEntity... profiles);
}
