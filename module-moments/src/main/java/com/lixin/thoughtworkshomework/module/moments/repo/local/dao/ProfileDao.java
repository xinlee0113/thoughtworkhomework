package com.lixin.thoughtworkshomework.module.repo.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lixin.thoughtworkshomework.module.repo.entity.ProfileEntity;
import com.lixin.thoughtworkshomework.module.repo.entity.TweetEntity;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/13.
 * 用户信息Dao
 */
@Dao
public interface ProfileDao {

    @Query("SELECT * FROM profile WHERE userName=:userName")
    LiveData<ProfileEntity> findProfileByName(String userName);

    @Query("SELECT * FROM tweet WHERE userName=:userName")
    LiveData<List<TweetEntity>> queryTweetsByUserName(String userName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProfileEntity... profiles);
}
