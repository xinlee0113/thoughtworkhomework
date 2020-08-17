package com.lixin.thoughtworkshomework.repo.local.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.lixin.thoughtworkshomework.repo.entity.TweetEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * @author lixin
 * @date 2020/8/13.
 */
@Dao
public interface TweetDao {
    @Query("SELECT * FROM tweet")
    DataSource.Factory<Integer, TweetEntity> queryTweetsByUserName();

    @Insert(onConflict = REPLACE)
    void save(TweetEntity tweet);
}
