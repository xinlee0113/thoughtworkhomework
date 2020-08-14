package com.lixin.thoughtworkshomework.repo.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

import java.util.List;

/**
 * @author lixin
 * @date 2020/8/13.
 */
@Dao
public interface TweetDao {
    @Query("SELECT * FROM tweet WHERE userName = :userName")
    LiveData<List<TweetEntity>> queryTweetsByUserName(String userName);
}
