package com.lixin.thoughtworkshomework.module.moments.repo.local.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.lixin.thoughtworkshomework.module.moments.repo.entity.TweetEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * @author lixin
 * @date 2020/8/13.
 * 朋友圈信息Dao
 */
@Dao
public interface TweetDao {
    /**
     * @return 朋友圈数据源
     */
    @Query("SELECT * FROM tweet")
    DataSource.Factory<Integer, TweetEntity> queryTweetsByUserName();

    /**
     * @param tweet 朋友圈Item
     */
    @Insert(onConflict = REPLACE)
    void save(TweetEntity tweet);

}
