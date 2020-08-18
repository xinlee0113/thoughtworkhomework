package com.lixin.thoughtworkshomework.module.repo.local.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.lixin.thoughtworkshomework.module.repo.entity.TweetEntity;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * @author lixin
 * @date 2020/8/13.
 * 朋友圈信息Dao
 */
@Dao
public interface TweetDao {
    @Query("SELECT * FROM tweet")
    DataSource.Factory<Integer, TweetEntity> queryTweetsByUserName();

    @Insert(onConflict = REPLACE)
    void save(TweetEntity tweet);

    @Insert(onConflict = REPLACE)
    void saveAll(List<TweetEntity> tweets);
}
